package com.vs.repositories

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rentomojo.repository.Repo
import com.vs.app.NotesApp
import com.vs.dao.NotesDao
import com.vs.database.NotesDatabase
import com.vs.models.ActionPerformed
import com.vs.models.Note
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import com.vs.utils.Result
import com.vs.utils.RxBus
import com.vs.utils.Utils
import com.vs.veronica.utils.C
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

/**
 * Created By Sachin
 */
class NotesRepo : Repo() {

    var notes: LiveData<List<Note>>? = NotesDatabase.getDatabase(NotesApp.instance!!).notesDao().getAllNotes()

    private val _noteAdded = MutableLiveData<Result<Note>>()
    val noteAdded: LiveData<Result<Note>> = _noteAdded


    fun addNote(context: Context, title: String, desc: String) {
        _showProgressBar.postValue(true)
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
                        _showProgressBar.postValue(false)
                    }

                })
    }

    fun deleteNote(context: Context, note: Note) {
        disposables.add(Observable.fromCallable {
            NotesDatabase.getDatabase(context).notesDao().delete(note.id)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                })

    }


    fun updateNote(context: Context, note: Note) {
        _showProgressBar.postValue(true)
        disposables.add(
                Observable.fromCallable {
                    NotesDatabase.getDatabase(context).notesDao()
                            .insert(Note(note.title, note.description))
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
        )
    }

    fun getNotes() {
        NotesApp.instance?.also {
            _showProgressBar.postValue(true)
            disposables.add(Observable.fromCallable {
                Observable.just(NotesDatabase.getDatabase(it).notesDao().getAllNotes())
            }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        _showProgressBar.postValue(false)
                        it.subscribe { data ->
                            Log.d("ComingHere", "Inside_showAllFruits ${listOf(notes)}")
                            notes = data
                        }

                    })
        }
    }


//
//
//    class NoteRepository(private val noteDao: NoteDao) {
//
//        private val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()
//
//        fun insert(note: Note) {
//            InsertNoteAsyncTask(
//                    noteDao
//            ).execute(note)
//        }
//
//        fun deleteAllNotes() {
//            DeleteAllNotesAsyncTask(
//                    noteDao
//            ).execute()
//        }
//
//        fun getAllNotes(): LiveData<List<Note>> {
//            return allNotes
//        }
//
//        private class InsertNoteAsyncTask(val noteDao: NoteDao) : AsyncTask<Note, Unit, Unit>() {
//
//            override fun doInBackground(vararg note: Note?) {
//                noteDao.insert(note[0]!!)
//            }
//        }
//
//
//        private class DeleteAllNotesAsyncTask(val noteDao: NoteDao) : AsyncTask<Unit, Unit, Unit>() {
//
//            override fun doInBackground(vararg p0: Unit?) {
//                noteDao.deleteAllNotes()
//            }
//        }
//
//    }


}
