package com.example.musicapplication.allsong

import android.widget.TextView
import com.example.musicapplication.convertDurationToFormatted
import com.example.musicapplication.database.DataSong

@androidx.databinding.BindingAdapter("sleepDurationFormatted")
fun TextView.setSleepDurationFormatted(item: DataSong?) {
    item?.let {
        text = convertDurationToFormatted(item.duration!!, context.resources)
    }
}
