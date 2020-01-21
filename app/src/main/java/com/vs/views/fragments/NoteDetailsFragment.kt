package com.vs.views.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vs.R
import com.vs.models.Note
import com.vs.veronica.utils.C
import kotlinx.android.synthetic.main.fragment_note_details.*



/**
 * Created By Sachin
 */
class NoteDetailsFragment : Fragment() {

    private var noteData: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.also {
            noteData = it.getSerializable(C.NOTE) as Note
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_note_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteData?.also { showNotesDetails(it) }

        btnGoToNoteList.setOnClickListener {
            activity?.also {
                Log.d("Cominghere", "InsidesetOnClickListener ")

                for (i in 0 until it.supportFragmentManager.backStackEntryCount) {
                    it.supportFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun showNotesDetails(note: Note) {
        tvNotesDetailsValue.text = "Title: "+note.title +"\n\n"+ "Description: "+note.description +"\n\n"+ "Timestamp: "+note.time
    }
}
