package com.vs.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rentomojo.repository.Repo
import com.vs.database.NotesDatabase
import com.vs.models.ActionPerformed
import com.vs.models.Note
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import com.vs.utils.Result
import com.vs.utils.RxBus
import com.vs.veronica.utils.C
import io.reactivex.schedulers.Schedulers

/**
 * Created By Sachin
 */
class NotesRepo : Repo() {

    private val _notes = MutableLiveData<Result<List<Note>>>()
    val notes: LiveData<Result<List<Note>>> = _notes

    private val _noteAdded = MutableLiveData<Result<Note>>()
    val noteAdded: LiveData<Result<Note>> = _noteAdded

    fun addNote(context: Context, title: String, desc: String) {
        disposables.add(Observable.fromCallable {
            Observable.just(NotesDatabase.getDatabase(context).notesDao().insert(Note(title, desc)))
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    it.subscribe {
                        val actionPerformed = ActionPerformed().apply {
                            action = C.ADD
                            note = Note(title, desc)
                        }
                        RxBus.actionPerformed.onNext(actionPerformed)
                        _noteAdded.postValue(Result.Success(Note(title, desc)))
                    }

                })
    }

    fun deleteNote(context: Context, note: Note) {
        disposables.add(Observable.fromCallable {
            Observable.just(NotesDatabase.getDatabase(context).notesDao().delete(note))
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    it.subscribe {
                        val actionPerformed = ActionPerformed().apply {
                            this.action = C.DELETE
                            this.note = note
                        }
                        RxBus.actionPerformed.onNext(actionPerformed)
                    }
                })
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
