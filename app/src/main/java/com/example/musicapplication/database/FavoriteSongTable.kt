package com.example.musicapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Bkav TuanTVb on 30/08/2022.
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


