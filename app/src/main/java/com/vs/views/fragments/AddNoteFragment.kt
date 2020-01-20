package com.vs.veronica.views.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.vs.R
import com.vs.models.Note
import com.vs.utils.RxBus
import com.vs.utils.Utils
import com.vs.viewmodels.NotesViewModel
import kotlinx.android.synthetic.main.fragment_add_note.*


/**
 * Created By Sachin
 */
class AddNoteFragment : Fragment() {

    private val notesViewModel by lazy { ViewModelProviders.of(this).get(NotesViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelChanges()
        btnSaveNote.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val title = noteTitleTextInputEditText?.text.toString()
        val desc = noteDescTextInputEditText?.text.toString()
        if (title.isEmpty() || desc.isEmpty()) {
            Utils.showToastMessage("Please enter required details!")
            return
        }
//        RxBus.addedNote.onNext(Note(title, desc))
        activity?.also { notesViewModel.addNote(it, title, desc) }
    }

    private fun observeViewModelChanges() {

    }

}
