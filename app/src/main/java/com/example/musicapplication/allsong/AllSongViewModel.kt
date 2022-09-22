package com.example.musicapplication.allsong

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapplication.database.DataSong
import com.example.musicapplication.database.DataSongRepository


class AllSongViewModel(private val dataSource: Application) : ViewModel() {

    private val dataSongRepository = DataSongRepository()

    public val songs = dataSongRepository.getSongs(dataSource)

    var isPlayedMusic = false

    var isPlayMusic  = false

    private val _songIsPlaying = MutableLiveData<DataSong>()
    val songIsPlaying: LiveData<DataSong>
        get() = _songIsPlaying

    private val _songClicked = MutableLiveData<String>()


    fun onDataSongClicked(id: String) {
        _songClicked.value = id

    }

    fun setSongIsPlaying(song: DataSong) {
        _songIsPlaying.value = song
    }
}


