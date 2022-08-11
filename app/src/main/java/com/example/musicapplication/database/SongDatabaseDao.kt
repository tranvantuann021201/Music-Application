package com.example.musicapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SongDatabaseDao {

    @Query("select * from SongTable")
    fun getAllSong(): List<SongTable>

}