package com.example.musicapplication

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

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
fun setImageAlbum(view: View, bitmap: Bitmap?, res: Resources) {
    view.background = BitmapDrawable(res,bitmap)
}