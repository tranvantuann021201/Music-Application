package com.example.musicapplication.allsong

import android.widget.TextView
import com.example.musicapplication.convertDurationToFormatted
import com.example.musicapplication.database.DataSong

@androidx.databinding.BindingAdapter("sleepDurationFormatted")
fun TextView.setSleepDurationFormatted(songs: DataSong) {
    songs?.let {
        text = convertDurationToFormatted(songs.duration!!, context.resources)
    }
//    songs?.forEach{
//        text = formatDuration(it.duration, context.resources)
//    }
}
