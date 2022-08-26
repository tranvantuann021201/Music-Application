package com.example.musicapplication.database

import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

class DataSongRepository {
    private val getMusicDataFormDeviceStorage = GetMusicDataFormDeviceStorage()
    fun getSongs(): MutableLiveData<List<DataSong>> {
        return getMusicDataFormDeviceStorage.getSong()
    }
}


class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)
