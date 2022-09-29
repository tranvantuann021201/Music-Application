package com.example.musicapplication.mediaplayback

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapplication.database.DataSong
import com.example.musicapplication.database.DataSongRepository

class MediaPlayBackViewModel(private val dataSource: Application
) : ViewModel(){

    private val dataSongRepository = DataSongRepository()
    val songs = dataSongRepository.getSongs(dataSource)

    private val _songIsPlaying = MutableLiveData<DataSong>()
    val songIsPlaying: LiveData<DataSong>
        get() = _songIsPlaying

    fun setSongIsPlaying(song: DataSong) {
        _songIsPlaying.value = song
    }
}