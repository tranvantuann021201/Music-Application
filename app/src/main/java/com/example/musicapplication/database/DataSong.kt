package com.example.musicapplication.database

data class DataSong(
    var songID: Int?,
    var songName: String?,
    var albumName: String?,
    var artists: String?,
    var duration: Long?,
    var data: String?
)