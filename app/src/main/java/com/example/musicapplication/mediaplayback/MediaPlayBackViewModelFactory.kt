package com.example.musicapplication.mediaplayback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicapplication.database.FavoriteSongDatabaseDao


class MediaPlayBackViewModelFactory(
    val database: FavoriteSongDatabaseDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MediaPlayBackViewModel::class.java)) {
            return MediaPlayBackViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}