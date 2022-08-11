package com.example.musicapplication.allsong

import android.util.Log
import androidx.lifecycle.ViewModel

class AllSongViewModel: ViewModel() {

    init {
        Log.i("GameViewModel", "GameViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }

}