package com.vs.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rentomojo.repository.Repo
import com.vs.database.NotesDatabase
import com.vs.models.Note
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import com.vs.utils.Result
import io.reactivex.schedulers.Schedulers

/**
 * Created By Sachin
 */
class NotesRepo : Repo() {

    private val _notes = MutableLiveData<Result<List<Note>>>()
    val notes: LiveData<Result<List<Note>>> = _notes


    fun addNote(context: Context, title: String, desc: String) {
        _showProgressBar.postValue(true)
        disposables.add(Observable.fromCallable {
            NotesDatabase.getDatabase(context).notesDao().insert(Note(title, desc))
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _showProgressBar.postValue(false)
                })
    }

    fun deletNote(context: Context, note: Note) {
        _showProgressBar.postValue(true)
        disposables.add(Observable.fromCallable {
            NotesDatabase.getDatabase(context).notesDao().delete(note)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    fun updateNote(context: Context, note: Note) {
        _showProgressBar.postValue(true)
        disposables.add(Observable.fromCallable {
            NotesDatabase.getDatabase(context).notesDao().insert(Note(note.title, note.description))
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    fun getNotes(context: Context) {
        _showProgressBar.postValue(true)
        disposables.add(Observable.fromCallable {
            Observable.just(NotesDatabase.getDatabase(context).notesDao().getAllNotes())
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _showProgressBar.postValue(false)
                    it.subscribe { notes ->
                        _notes.postValue(Result.Success(notes))
                    }

                })

    }


}
