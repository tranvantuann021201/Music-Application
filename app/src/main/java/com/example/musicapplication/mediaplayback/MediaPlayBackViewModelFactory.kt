package com.example.musicapplication.mediaplayback

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MediaPlayBackViewModelFactory(
    val database: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MediaPlayBackViewModel::class.java)) {
            return MediaPlayBackViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}