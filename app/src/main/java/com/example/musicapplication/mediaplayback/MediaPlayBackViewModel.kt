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

    private val _songIsPlaying = MutableLiveData<ArrayList<DataSong>>()
    val songIsPlaying: LiveData<ArrayList<DataSong>>
        get() = _songIsPlaying


    fun setSongIsPlaying(song: ArrayList<DataSong>) {
        _songIsPlaying.value = song
    }
}