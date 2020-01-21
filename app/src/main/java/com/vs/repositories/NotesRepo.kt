package com.vs.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rentomojo.repository.Repo
import com.vs.app.NotesApp
import com.vs.database.NotesDatabase
import com.vs.models.Note
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import com.vs.utils.Result
import com.vs.utils.Utils
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created By Sachin
 */
class NotesRepo : Repo() {

    var notes: LiveData<List<Note>>? = NotesDatabase.getDatabase(NotesApp.instance!!).notesDao().getAllNotes()

    private val _noteAddedOrUpdated = MutableLiveData<Result<Note>>()
    val noteAddedOrUpdated: LiveData<Result<Note>> = _noteAddedOrUpdated


    fun addNote(context: Context, title: String, desc: String, time: String) {
        _showProgressBar.postValue(true)
        disposables.add(Observable.fromCallable {
            Observable.just(NotesDatabase.getDatabase(context).notesDao().insert(Note(title, desc, time)))
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    it.subscribe {
                        _noteAddedOrUpdated.postValue(Result.Success(Note(title, desc,time)))
                        _showProgressBar.postValue(false)
                    }

                })
    }

    fun deleteNote(context: Context, note: Note) {
        Log.d("ComingHere", "Inside_deleteNote ${note.id}")
        _showProgressBar.postValue(true)
        disposables.add(Observable.fromCallable {
            NotesDatabase.getDatabase(context).notesDao().delete(note.id)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _showProgressBar.postValue(false)
                })

    }


    fun updateNote(context: Context, noteData: Note,time:String) {
        Log.d("ComingHere", "Inside_updateNote ${noteData.id.toString() + " " + noteData.title + " " + noteData.description}")
        _showProgressBar.postValue(true)
        disposables.add(Observable.fromCallable {
            NotesDatabase.getDatabase(context).notesDao()
                    .update(noteData.id, noteData.title, noteData.description,time)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _noteAddedOrUpdated.postValue(Result.Success(noteData))
                    _showProgressBar.postValue(false)
                })

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
