package com.example.musicapplication.mediaplayback

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapplication.database.DataSong

class MediaPlayBackViewModel(
    database: Application
) : ViewModel(){
    //get attributes data of song
    private val _songName = MutableLiveData<String?>()
    val songName: LiveData<String?>
        get() = _songName

    private val _songArtist = MutableLiveData<String?>()
    val songArtist: LiveData<String?>
        get() = _songArtist

    private val _songPicture = MutableLiveData<Bitmap?>()
    val songPicture: LiveData<Bitmap?>
        get() = _songPicture

    private val _songIsPlaying = MutableLiveData<DataSong>()
    val songIsPlaying: LiveData<DataSong>
        get() = _songIsPlaying

    fun setSongIsPlaying(song: DataSong) {
        _songIsPlaying.value = song
    }
}