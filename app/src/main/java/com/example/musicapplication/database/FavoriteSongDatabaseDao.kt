package com.example.musicapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Created by Bkav TuanTVb on 30/08/2022.
 */

@Dao
interface FavoriteSongDatabaseDao {

    /* Bkav TuanTVb: Update bài hát*/
    @Update
    suspend fun update(song: FavoriteSongsDatabase)
    /* Bkav TuanTVb: Thêm bài hát ưa thích*/
    @Insert
    suspend fun insert(song: FavoriteSongsDatabase)
    /* Bkav TuanTVb: Lấy ra tất cả bài hát ưa thích*/
    @Query("SELECT * FROM SongTable")
    fun getAllSongs(): List<FavoriteSongsDatabase>
    @Query("DELETE FROM SongTable WHERE song_ID = :key ")
    fun removeFavoriteSong(key:Int)

}