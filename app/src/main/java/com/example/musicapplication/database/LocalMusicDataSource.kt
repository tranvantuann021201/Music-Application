package com.example.musicapplication.database

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData

/**
 * Created by Bkav TuanTVb on 30/08/2022.
 */

class LocalMusicDataSource {

    @SuppressLint("Recycle")
    fun getSong(context: Context): MutableLiveData<List<DataSong>> {
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
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

        val liveDataSongs = MutableLiveData<List<DataSong>>()

        val sortOder = MediaStore.Audio.Media.TITLE
        val songs: ArrayList<DataSong> = ArrayList()

        val cursor: Cursor? =
            context.contentResolver.query(uri, projection, selection, null, sortOder)

        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val song = DataSong(
                    cursor.getLong(7),
                    cursor.getString(0),
                    cursor.getString(5),
                    cursor.getString(1),
                    cursor.getLong(4),
                    cursor.getString(2),
                    cursor.getString(6)
                )
                songs.add(song)
                cursor.moveToNext()
            }
        }
        liveDataSongs.value = songs;
        return liveDataSongs
    }

}


