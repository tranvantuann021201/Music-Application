package com.example.musicapplication.database
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
)

