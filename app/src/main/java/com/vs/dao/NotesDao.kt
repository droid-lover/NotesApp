package com.vs.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vs.models.Note

/**
 * Created By Sachin
 */
@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    //
//    @Delete
//    fun delete(note: Note)
    @Query("DELETE FROM Note WHERE `id` =:id")
    fun delete(id:Int)

    @Update
    fun update(note: Note)

    @Query("Select * from Note")
    fun getAllNotes(): LiveData<List<Note>>

}