package com.example.musicapplication.database

//class SongTable {
//}


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents one night's sleep through start, end times, and the sleep quality.
 */
@Entity
data class SongTable(
    @PrimaryKey(autoGenerate = true)
    var songId: Int = 0,

    @ColumnInfo(name = "song_Name")
    var songName: Long = 0L)
