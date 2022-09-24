package com.example.musicapplication.database

import android.content.Context
import androidx.lifecycle.MutableLiveData

/**
 * Created by Bkav TuanTVb on 30/08/2022.
 */

class DataSongRepository {
    private val getMusicDataFormDeviceStorage = GetMusicDataFormDeviceStorage()
    fun getSongs(context: Context): MutableLiveData<List<DataSong>> {
        return getMusicDataFormDeviceStorage.getSong(context)
    }
}


