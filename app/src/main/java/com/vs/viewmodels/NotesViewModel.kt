package com.vs.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.vs.repositories.NotesRepo


class NotesViewModel : ViewModel() {

    private val repo = NotesRepo()
    var showProgressBar = repo.showProgressBar
    var notes = repo.notes

    override fun onCleared() {
        super.onCleared()
        repo.onCleared()
    }

    fun addNote(context: Context, title: String, desc: String) = repo.addNote(context, title, desc)
    fun getNotes(context: Context) = repo.getNotes(context)
//    fun getNotes() = repo.getNotes()
//    fun getNotes() = repo.getNotes()
}

