package com.example.musicapplication.database

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
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
    fun getPicture(context: Context): Bitmap? {
        var art: Bitmap = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.bg_default_album_art
        )
        val uri = Uri.parse(data)
        val mmr = MediaMetadataRetriever()
        val bfo = BitmapFactory.Options()
        mmr.setDataSource(context, uri)
        val rawArt: ByteArray? = mmr.embeddedPicture
        if (null != rawArt) {
            art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.size, bfo)
        }
        return art
    }
}



