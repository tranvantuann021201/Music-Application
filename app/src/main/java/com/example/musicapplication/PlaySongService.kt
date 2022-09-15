package com.example.musicapplication

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.MutableLiveData
import com.example.musicapplication.database.DataSong
import com.example.musicapplication.database.DataSongRepository

class PlaySongService() : Service() {

    private val dataSongRepository = DataSongRepository()
    private val dataSong = MutableLiveData<List<DataSong>>()
    private lateinit var player: MediaPlayer

    // Binder given to clients
    private val binder = LocalBinder()

    companion object {
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

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
            if (player.isPlaying) {
                player.stop()
            }
            player = MediaPlayer.create(applicationContext, Uri.parse(path))
            player.start()
        }
    }

    fun stopMusic() {
        player.stop()
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): PlaySongService = this@PlaySongService
    }

    override fun onBind(intent: Intent): IBinder? {
        player = MediaPlayer()
        return binder
    }
}