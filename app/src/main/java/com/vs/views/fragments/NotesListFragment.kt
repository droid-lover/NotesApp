package com.vs.views.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.vs.R
import com.vs.adapters.NotesAdapter
import com.vs.app.NotesApp
import com.vs.models.Note
import com.vs.utils.ItemOffsetDecoration
import com.vs.utils.Utils
import com.vs.viewmodels.NotesViewModel
import com.vs.views.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_notes_list.*
import com.vs.utils.Result
import com.vs.utils.RxBus
import com.vs.veronica.views.fragments.AddNoteFragment
import io.reactivex.disposables.CompositeDisposable

/**
 * Created By Sachin
 */
class NotesListFragment : Fragment() {

    private val notesViewModel by lazy {
        ViewModelProviders.of(this).get(NotesViewModel::class.java)
    }
    private val compositeDisposable = CompositeDisposable()
    private var notesAdapter: NotesAdapter? = null
    private var notesData: ArrayList<Note> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("ComingHere", "onCreateView ")
        return inflater.inflate(R.layout.fragment_notes_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        Log.d("ComingHere", "onStart ")

    }

    override fun onResume() {
        super.onResume()
        Log.d("ComingHere", "onResume ")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel.getNotes(view.context)
        observeViewModelChanges()
        btnAddNote?.setOnClickListener {
            (view.context as HomeActivity).supportFragmentManager.beginTransaction()
                .add(R.id.rlContainer, AddNoteFragment()).addToBackStack("AddNoteFragment")
                .commit()
        }
    }

    private fun observeViewModelChanges() {
        notesViewModel.notes.observe(this, androidx.lifecycle.Observer {
            when (it) {
                is Result.Success<List<Note>> -> {
                    it.data.also { notes ->
                        if (notes.isNullOrEmpty().not()) {
                            setUpNotesList(notes)
                        }
                    }
                }
                is Result.Failure -> {
                    Utils.showToastMessage("Something went wrong ${it.throwable.localizedMessage}")
                    Log.d("ComingHere", "InsideFailure")
                }
            }
        })

        notesViewModel.showProgressBar.observe(this, androidx.lifecycle.Observer
        { if (it) Utils.showProgressDialog(activity!!) else Utils.hideProgressDialog() })

        compositeDisposable.add(RxBus.addedNote.subscribe {
            Log.d("ComingHere", "InsideaddedNoteSuccess  $it}")
            notesData.add(it)
            notesAdapter?.notifyDataSetChanged()
            Log.d("Cominghere", "InsideaddedNoteSuccess ${notesData.size}")
        })

    }

    private fun setUpNotesList(notes: List<Note>) {
        activity?.also {
            notesData = ArrayList(notes)
            notesAdapter = NotesAdapter(it, notesData!!)
            rvNotes.visibility = View.VISIBLE
            setOtherViewsVisibility(View.GONE)

            rvNotes?.apply {
                layoutManager = LinearLayoutManager(it)
                adapter = notesAdapter
            }
            if (rvNotes.itemDecorationCount == 0) {
                val spacing = resources.getDimensionPixelOffset(R.dimen.default_spacing_small)
                rvNotes.addItemDecoration(ItemOffsetDecoration(spacing))
            }
            rvNotes?.startLayoutAnimation()
        }
    }

    private fun setOtherViewsVisibility(boolean: Int) {
//        btnAddNote.visibility = boolean
        tvNoData.visibility = boolean
        noDataAnimationView.visibility = boolean
    }

}
