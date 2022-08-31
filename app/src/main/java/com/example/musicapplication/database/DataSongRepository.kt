package com.example.musicapplication.database

import android.content.Context
import androidx.lifecycle.MutableLiveData

class DataSongRepository {
    private val getMusicDataFormDeviceStorage = GetMusicDataFormDeviceStorage()
    fun getSongs(context: Context): MutableLiveData<List<DataSong>> {
        return getMusicDataFormDeviceStorage.getSong(context)
    }
}


