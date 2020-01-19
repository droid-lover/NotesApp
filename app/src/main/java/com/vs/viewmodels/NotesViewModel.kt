package com.vs.viewmodels

import androidx.lifecycle.ViewModel
import com.vs.repositories.NotesRepo


class NotesViewModel : ViewModel() {

    private val repo = NotesRepo()
    var showProgressBar = repo.showProgressBar


    override fun onCleared() {
        super.onCleared()
        repo.onCleared()
    }

    fun getNotes() = repo.getNotes()
}

