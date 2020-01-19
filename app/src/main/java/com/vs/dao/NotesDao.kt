package com.vs.dao

import androidx.room.*
import com.vs.models.Note

/**
 * Created By Sachin
 */
@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)

    @Update
    fun update(note: Note)

    @Query("Select * from Note")
    fun getAllNotes(): ArrayList<Note>
}