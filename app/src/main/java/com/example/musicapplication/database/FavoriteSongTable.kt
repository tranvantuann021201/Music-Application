package com.example.musicapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Represents one night's sleep through start, end times, and the sleep quality.
 */

@Entity
data class SongTable(
    @PrimaryKey(autoGenerate = true)
    var ID: Int = 0,
    @ColumnInfo(name = "song_ID")
    var ID_PROVIDER: Int= 0,
    @ColumnInfo(name = "song_rating")
    var IS_FAVORITE: Int = 0,
    @ColumnInfo(name = "count_of_play")
    var COUNT_OF_PLAY: Int = 0
)


