package com.vs.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.vs.models.Note
import com.vs.repositories.NotesRepo


class NotesViewModel : ViewModel() {

    private val repo = NotesRepo()
    var showProgressBar = repo.showProgressBar
    var notes = repo.notes
    var noteAddedOrUpdated = repo.noteAddedOrUpdated

    override fun onCleared() {
        super.onCleared()
        repo.onCleared()
    }

    fun addNote(context: Context, title: String, desc: String, time: String) = repo.addNote(context, title, desc,time)
    fun getNotes() = repo.getNotes()
    fun deleteNote(context: Context, note: Note) = repo.deleteNote(context,note)
    fun updateNote(context: Context, note: Note,time:String) = repo.updateNote(context,note,time)
}

