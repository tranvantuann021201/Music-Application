package com.example.musicapplication.allsong

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicapplication.database.FavoriteSongDatabaseDao

class AllSongViewModelFactory(
    val database: FavoriteSongDatabaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllSongViewModel::class.java)) {
            return AllSongViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}