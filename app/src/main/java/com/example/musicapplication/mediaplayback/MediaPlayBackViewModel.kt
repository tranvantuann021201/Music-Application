package com.example.musicapplication.mediaplayback

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapplication.database.DataSong
import com.example.musicapplication.database.FavoriteSongDatabaseDao
import com.example.musicapplication.database.SongTable
import kotlinx.coroutines.launch

/**
 * Created by Bkav TuanTVb on 04/09/2022.
 */

class MediaPlayBackViewModel(private val application: Application, val roomDatabase: FavoriteSongDatabaseDao
) : ViewModel(){

    private val _songIsPlaying = MutableLiveData<DataSong>()
    val songIsPlaying: LiveData<DataSong>
        get() = _songIsPlaying

    val context = application
    val resources = application.resources!!

    /**
     *  Bkav TuanTVb: set data bài nhạc đang chạy
     *  */
    fun setSongIsPlaying(song: DataSong) {
        _songIsPlaying.value = song
    }

    /**
     * Bkav TuanTVb: insert bai hat vao room
     */
    private suspend fun insert(songRoom: SongTable) {
        roomDatabase.insert(songRoom)
    }

    private suspend fun remove(id: Int) {
        roomDatabase.removeFavoriteSong(id)
    }

    /**
     * Bkav TuanTVb: Kiểm tra bài nhạc đã tồn tại trong Favorite hay chưa
     */
    private fun count(id: Int): Int {
        return roomDatabase.count(id)
    }

    /**
     * Bkav TuanTVb: Insert bài hát vào Favorites khi ấn like
     */
    fun onClickLikeButton(id: Int) {
        viewModelScope.launch {
            val newFavoriteSongs = SongTable()
            newFavoriteSongs.ID_PROVIDER = id
            newFavoriteSongs.IS_FAVORITE = 1
            insert(newFavoriteSongs)
            viewModelScope.launch { roomDatabase.getAllSongs() }
        }
    }

    /**
     * Bkav TuanTVb: remove bai hat khi an dislike
     */

    fun onClickDislikeButton(id: Int) {
        viewModelScope.launch {
            remove(id)
        }
    }

    /**
     * Bkav TuanTVb: Kiểm tra bài nhạc tồn tại trong favorite
     */
    fun checkExistSong(id: Int): Boolean {
        val count = count(id)
        return count.equals(0)
    }
}
