package com.vs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vs.dao.NotesDao
import com.vs.models.Note

/**
 * Created By Sachin
 */
@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun notesDao(): NotesDao

    companion object {
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase {
            if (INSTANCE != null) {
                return INSTANCE!!
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context,
                        NotesDatabase::class.java,
                        "notes_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}