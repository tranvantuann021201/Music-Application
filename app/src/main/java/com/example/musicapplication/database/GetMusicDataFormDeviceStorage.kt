package com.example.musicapplication.database

import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.example.musicapplication.MainActivity

class GetMusicDataFormDeviceStorage {

    fun getSong(): ArrayList<DataSong> {
        val songs: ArrayList<DataSong> = ArrayList()
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
        val projection = arrayOf(
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media._ID
        )
        val sortOder = MediaStore.Audio.AudioColumns.TITLE
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val cursor: Cursor? = MainActivity.
    }

}