package com.example.musicapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteSongDatabaseDao {

    //update bai hat
    @Update
    suspend fun update(song: FavoriteSongsDatabase)
    //them bai hat ua thich
    @Insert
    suspend fun insert(song: FavoriteSongsDatabase)
    // lay ra tat ca bai hat ua thich
    @Query("SELECT * FROM SongTable")
    fun getAllSongs(): List<FavoriteSongsDatabase>
    @Query("DELETE FROM SongTable WHERE song_ID = :key ")
    fun removeFavoriteSong(key:Int)

}