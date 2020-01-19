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
import com.vs.models.Note
import com.vs.utils.Utils
import com.vs.viewmodels.NotesViewModel
import com.vs.views.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_notes_list.*
import com.vs.utils.Result
import com.vs.veronica.views.fragments.AddNoteFragment

/**
 * Created By Sachin
 */
class NotesListFragment : Fragment() {

    private val notesViewModel by lazy { ViewModelProviders.of(this).get(NotesViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel.getNotes(view.context)
        observeViewModelChanges()
        btnAddNote?.setOnClickListener {
            (view.context as HomeActivity).supportFragmentManager.beginTransaction()
                    .add(R.id.rlContainer, AddNoteFragment()).addToBackStack("AddNoteFragment")
                    .commitAllowingStateLoss()
        }
    }

    private fun observeViewModelChanges() {
        notesViewModel.notes.observe(this, androidx.lifecycle.Observer {
            when (it) {
                is Result.Success<List<Note>> -> {
                    Log.d("ComingHere", "InsideSuccess ${Gson().toJson(it.data)}")
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

    }

    private fun setUpNotesList(notes: List<Note>) {
        activity?.also {
            rvNotes?.apply {
                layoutManager = LinearLayoutManager(it)

            }
        }
    }

}
