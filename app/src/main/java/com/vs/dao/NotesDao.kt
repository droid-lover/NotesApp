package com.vs.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vs.models.Note
import androidx.room.Update


/**
 * Created By Sachin
 */
@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Query("DELETE FROM Note WHERE `id` =:id")
    fun delete(id: Int)

    @Query("UPDATE Note SET title = :title,description=:description,time=:time WHERE id = :id")
    fun update(id: Int, title: String,description:String,time:String): Int

    @Query("Select * from Note ORDER BY Note.time DESC")
    fun getAllNotes(): LiveData<List<Note>>

}