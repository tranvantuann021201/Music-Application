package com.example.musicapplication.mediaplayback

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MediaPlayBackViewModel: ViewModel() {
    //get attributes data of song
    private val _songName = MutableLiveData<String?>()
    val songName: MutableLiveData<String?>
        get() = _songName

    private val _songArtist = MutableLiveData<String?>()
    val songArtist: MutableLiveData<String?>
        get() = _songArtist

    private val _songPicture = MutableLiveData<Bitmap?>()
    val songPicture: MutableLiveData<Bitmap?>
        get() = _songPicture
}