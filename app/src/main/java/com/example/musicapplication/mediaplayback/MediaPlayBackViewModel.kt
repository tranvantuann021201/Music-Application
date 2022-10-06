package com.example.musicapplication.mediaplayback

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapplication.database.DataSong
import com.example.musicapplication.database.DataSongRepository

/**
 * Created by Bkav TuanTVb on 04/09/2022.
 */

class MediaPlayBackViewModel(private val application: Application
) : ViewModel(){

    private val dataSongRepository = DataSongRepository()
    val songs = dataSongRepository.getSongs(application)

    private val _songIsPlaying = MutableLiveData<DataSong>()
    val songIsPlaying: LiveData<DataSong>
        get() = _songIsPlaying

    val context = application

}