package com.example.musicapplication

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings
import androidx.lifecycle.MutableLiveData
import com.example.musicapplication.database.DataSong
import com.example.musicapplication.database.DataSongRepository

class PlaySongService() : Service() {

    private val dataSongRepository = DataSongRepository()
    private val dataSong = MutableLiveData<List<DataSong>>()
    private lateinit var player : MediaPlayer

    companion object {
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        player = MediaPlayer.create(applicationContext, Settings.System.DEFAULT_RINGTONE_URI)

        player.isLooping = true

        player.start()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        player.stop()
    }

    fun playMusic(path: String) {
        player?.let {
            if(player.isPlaying){
                player.stop()
                player.setDataSource(path)
                player.prepare()
            }
        }
    }



    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}