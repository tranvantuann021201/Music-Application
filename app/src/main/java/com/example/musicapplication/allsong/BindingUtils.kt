package com.example.musicapplication.allsong

import android.widget.TextView
import com.example.musicapplication.database.DataSong
import com.example.musicapplication.formatDuration

@androidx.databinding.BindingAdapter("sleepDurationFormatted")
fun TextView.setSleepDurationFormatted(songs: DataSong) {
    songs?.let {
        text = formatDuration(songs)
    }
//    songs?.forEach{
//        text = formatDuration(it.duration, context.resources)
//    }
}
