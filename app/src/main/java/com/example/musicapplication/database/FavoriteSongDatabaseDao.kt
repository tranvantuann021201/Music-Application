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
    suspend fun update(song: SongTable)
    /* Bkav TuanTVb: Thêm bài hát ưa thích*/
    @Insert
    suspend fun insert(song: SongTable)
    /* Bkav TuanTVb: Lấy ra tất cả bài hát ưa thích*/
    @Query("SELECT * FROM favorite_songs_provider")
    fun getAllSongs(): List<SongTable>
    /* Bkav TuanTVb: Xoá bản ghi khỏi database*/
    @Query("DELETE FROM favorite_songs_provider WHERE song_ID = :key ")
    fun removeFavoriteSong(key:Int)
    /* Bkav TuanTVb: cont != 0, bản ghi đã tồn tại*/
    @Query("SELECT COUNT(song_ID) FROM favorite_songs_provider WHERE song_ID = :id")
    fun count(id: Int): Int
}