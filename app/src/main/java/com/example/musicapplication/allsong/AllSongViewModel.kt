package com.example.musicapplication.allsong

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapplication.R
import com.example.musicapplication.database.DataSong
import com.example.musicapplication.database.DataSongRepository

/**
 * Created by Bkav TuanTVb on 30/08/2022.
 */

class AllSongViewModel(private val dataSource: Application) : ViewModel() {

    private val dataSongRepository = DataSongRepository()

    val songs = dataSongRepository.getSongs(dataSource)

    var isPlayedMusic = false

    var isPlayMusic  = false

    private val _songIsPlaying = MutableLiveData<DataSong>()
    val songIsPlaying: LiveData<DataSong>
        get() = _songIsPlaying

    val resources = dataSource.resources!!

    fun onDataSongClicked(data: String) {
    }

    fun setSongIsPlaying(song: DataSong) {
        _songIsPlaying.value = song
    }
    /**
     * Bkav TuanTVb:lay anh bia ra de chuyen sang thong bao
     */
    fun getPicture(song: DataSong?): Bitmap? {
        song?.let {
            var art: Bitmap = BitmapFactory.decodeResource(
                resources,
                R.drawable.bg_default_album_art
            )
            val uri = Uri.parse(song.data)
            val mmr = MediaMetadataRetriever()
            val bfo = BitmapFactory.Options()
            mmr.setDataSource(dataSource, uri)
            val rawArt: ByteArray? = mmr.embeddedPicture
            if (null != rawArt) {
                art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.size, bfo)
            }
            return art
        }
        return null
    }
}


