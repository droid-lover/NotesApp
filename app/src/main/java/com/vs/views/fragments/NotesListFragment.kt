package com.vs.views.fragments


import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.vs.R
import com.vs.adapters.NotesAdapter
import com.vs.models.Note
import com.vs.utils.ItemOffsetDecoration
import com.vs.utils.Utils
import com.vs.viewmodels.NotesViewModel
import kotlinx.android.synthetic.main.fragment_notes_list.*
import com.vs.utils.RxBus
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel.getNotes()
        observeViewModelChanges()
        btnAddNote?.setOnClickListener { goToAddNoteScreen() }
    }

    private fun goToAddNoteScreen() {
        activity?.also {
            it.supportFragmentManager.beginTransaction()
                    .add(R.id.rlContainer, AddNoteFragment()).addToBackStack("AddNoteFragment")
                    .commitAllowingStateLoss()
        }
    }

    private fun observeViewModelChanges() {

        notesViewModel.notes?.observe(this,
                Observer<List<Note>> { list ->
                    list?.let {
                        Log.d("ComingHere", "InsideObserve ${list.size}")
                        setUpNotesList(list)
                    }
                })

        notesViewModel.showProgressBar.observe(this, Observer
        { if (it) Utils.showProgressDialog(activity!!) else Utils.hideProgressDialog() })


        compositeDisposable.add(RxBus.showActionDailog.subscribe {
            performActions(it)
        })

    }

    private fun setUpNotesList(list: List<Note>) {
        setOtherViewsVisibility(list.size)
        activity?.also {
            if (list.isNotEmpty()) {
                notesAdapter = NotesAdapter(it, list)
                rvNotes?.apply {
                    layoutManager = LinearLayoutManager(it)
                    adapter = notesAdapter
                }
                if (rvNotes.itemDecorationCount == 0) {
                    val spacing = resources.getDimensionPixelOffset(R.dimen.default_spacing_small)
                    rvNotes.addItemDecoration(ItemOffsetDecoration(spacing))
                }
            }
        }
    }

    private fun setOtherViewsVisibility(notesSize: Int) {
        if (notesSize > 0) {
            tvNoData.visibility = View.GONE
            noDataAnimationView.visibility = View.GONE
            rvNotes.visibility = View.VISIBLE
        } else {
            tvNoData.visibility = View.VISIBLE
            noDataAnimationView.visibility = View.VISIBLE
            rvNotes.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }


    private fun performActions(note: Note) {
        Log.d("ComingHere", "Inside_showDialog ${note.title}")
        val dialogBuilder = AlertDialog.Builder(activity!!)
        dialogBuilder.setMessage("Do you want to delete this Note?")
                .setPositiveButton("Delete") { dialog, _ -> deleteNote(dialog, note) }
                .setNegativeButton("Edit") { dialog, _ -> dialog.cancel() }

        val alert = dialogBuilder.create()
        alert.setTitle("Perform Action")
        alert.show()
    }

    private fun deleteNote(dialog: DialogInterface, note: Note) {
        dialog.cancel()
        activity?.also { notesViewModel.deleteNote(it, note) }
    }
}
