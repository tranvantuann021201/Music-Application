package com.example.musicapplication.allsong

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapplication.database.DataSong
import com.example.musicapplication.database.DataSongRepository

/**
 * Created by Bkav TuanTVb on 30/08/2022.
 */

class AllSongViewModel(private val dataSource: Application) : ViewModel() {

    private val dataSongRepository = DataSongRepository()

    val songs = dataSongRepository.getSongs(dataSource)

    var isPlayedMusic = false

    var isPlayMusic  = false

    private val _songIsPlaying = MutableLiveData<DataSong>()
    val songIsPlaying: LiveData<DataSong>
        get() = _songIsPlaying

    val resources = dataSource.resources!!

    fun onDataSongClicked(data: String) {
    }

    fun setSongIsPlaying(song: DataSong) {
        _songIsPlaying.value = song
    }
}


