package com.example.musicapplication.mediaplayback

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicapplication.database.FavoriteSongDatabaseDao

/**
 * Created by Bkav TuanTVb on 04/09/2022.
 */


class MediaPlayBackViewModelFactory(
    val database: Application, val roomDatabase: FavoriteSongDatabaseDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MediaPlayBackViewModel::class.java)) {
            return MediaPlayBackViewModel(database,roomDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}