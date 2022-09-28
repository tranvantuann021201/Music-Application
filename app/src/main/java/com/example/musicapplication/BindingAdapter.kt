package com.example.musicapplication

import android.app.Application
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicapplication.database.DataSong
import com.example.musicapplication.database.DataSongRepository

/**
 * Created by Bkav TuanTVb on 24/09/2022.
 */

@BindingAdapter("durationSong")
/**
 * Bkav TuanTVb: Xử lý chuyển đổi doration về dạng thời gian
 */
fun formatDurationSong(view: TextView, duration: Long){
    val stringBuilder = StringBuilder()
    stringBuilder.apply {
        append("${duration / 1000 / 60}:")
        append("${duration / 10000}")
    }
    view.text = Html.fromHtml(stringBuilder.toString(), Html.FROM_HTML_MODE_LEGACY)
}

/**
 * Bkav TuanTVb: Xử lý chuyển đổi ảnh bìa album về dạng Bitmap
 */
@BindingAdapter("bitMap", "resources", requireAll = true)
fun setImageAlbum(view: View, bitmap: Bitmap?, res: Resources?) {
    view.background = BitmapDrawable(res,bitmap)
}

class GetPictureFormDataMusic(private val dataSource: Application) {

    private val dataSongRepository = DataSongRepository()

    val songs = dataSongRepository.getSongs(dataSource)

    private val _songIsPlaying = MutableLiveData<DataSong>()
    val songIsPlaying: LiveData<DataSong>
        get() = _songIsPlaying

    val resources = dataSource.resources!!

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
