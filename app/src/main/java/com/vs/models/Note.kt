package com.vs.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created By Sachin
 */

@Entity(indices = arrayOf(Index(value = ["title", "description"]/*, unique = true*/)))
data class Note(
        @ColumnInfo
        var title: String,
        @ColumnInfo
        var description: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}