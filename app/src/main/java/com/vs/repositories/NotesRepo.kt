package com.vs.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rentomojo.repository.Repo
import com.vs.models.Note

/**
 * Created By Sachin
 */
class NotesRepo : Repo() {

    private val _notes = MutableLiveData<Result<Note>>()
    val notes: LiveData<Result<Note>> = _notes


    fun getNotes() {
        _showProgressBar.postValue(true)

    }


}
