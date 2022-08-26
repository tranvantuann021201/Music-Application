package com.example.musicapplication.database

import android.annotation.SuppressLint
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import com.example.musicapplication.MainActivity

class GetMusicDataFormDeviceStorage {

    @SuppressLint("Recycle")
    fun getSong(): MutableLiveData<List<DataSong>> {
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

        val sortOder = MediaStore.Audio.AudioColumns.TITLE
        val songs: ArrayList<DataSong> = ArrayList()

        val cursor: Cursor? =
            MainActivity().contentResolver.query(uri, projection, selection, null, sortOder)

        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {

                //songs.add(mCursor.getString(mCursor.getColumnIndex(dbAdapter.KEY_NAME))); //add the item
                val song = DataSong(
                    cursor.getInt(7),
                    cursor.getString(0),
                    cursor.getString(5),
                    cursor.getString(1),
                    cursor.getLong(5),
                    cursor.getString(2)
                    )

                songs.add(song)
                cursor.moveToNext()
            }
        }
        liveDataSongs.value = songs;
        return liveDataSongs
    }

}


