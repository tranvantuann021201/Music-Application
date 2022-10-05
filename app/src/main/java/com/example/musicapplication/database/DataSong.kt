package com.example.musicapplication.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import com.example.musicapplication.MainActivity
import com.example.musicapplication.R
import java.io.Serializable

/**
 * Created by Bkav TuanTVb on 30/08/2022.
 */
data class DataSong(
    var songID: Long?,
    var songName: String?,
    var albumName: String?,
    var artists: String?,
    var duration: Long?,
    var data: String,
    var imgAlbum: String?
): Serializable {
    /**
     * Bkav TuanTVb:lay anh bia ra de chuyen sang thong bao
     */
    fun getPicture(song: DataSong?): Bitmap? {

        song?.let {
            var art: Bitmap = BitmapFactory.decodeResource(
                MainActivity.getContext().resources,
                R.drawable.bg_default_album_art
            )
            val uri = Uri.parse(song.data)
            val mmr = MediaMetadataRetriever()
            val bfo = BitmapFactory.Options()
            mmr.setDataSource(MainActivity.getContext(), uri)
            val rawArt: ByteArray? = mmr.embeddedPicture
            if (null != rawArt) {
                art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.size, bfo)
            }
            return art
        }
        return null
    }
}



