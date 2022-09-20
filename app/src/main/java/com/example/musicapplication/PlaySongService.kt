package com.example.musicapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.IBinder
import android.transition.TransitionInflater.from
import android.view.LayoutInflater.from
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.from
import androidx.core.content.getSystemService
import androidx.core.hardware.fingerprint.FingerprintManagerCompat.from
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider.Factory.Companion.from
import com.example.musicapplication.allsong.AllSongAdapter.ViewHolder.Companion.from
import com.example.musicapplication.database.DataSong
import com.example.musicapplication.database.DataSongRepository
import java.nio.file.attribute.AclEntry
import java.util.*

class PlaySongService() : Service() {

    private val dataSongRepository = DataSongRepository()
    private val dataSong = MutableLiveData<List<DataSong>>()
    private lateinit var player: MediaPlayer

    // Binder given to clients
    private val binder = LocalBinder()

    // Get the layouts to use in the custom notification
    private val notificationLayout = RemoteViews(packageName, R.layout.music_notification)

    companion object {
        const val CHANEL_ID = "chanelID"
        const val CHANEL_NAME = "chanelName"
        const val NOTIF_ID = 0
    }

    val notifManager = NotificationManagerCompat.from(this)

    // Apply the layouts to the notification
    val customNotification = NotificationCompat.Builder(applicationContext, CHANEL_ID)
        .setContentText("CntText Thử hiện lên cái")
        .setContentTitle("CntTitble Xem nó như nào")
        .setSmallIcon(R.drawable.stat_notify_musicplayer)
        .setStyle(NotificationCompat.DecoratedCustomViewStyle())
        .setCustomContentView(notificationLayout)
        .build()

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

    fun playAndPauseMusic() {
        if (player.isPlaying) {
            player.pause()
        } else
            player.start()
    }

    fun getStatusMusic(): Boolean {
        return player.isPlaying
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

    fun createNotifChanel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chanel = NotificationChannel(CHANEL_ID, CHANEL_NAME,NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.LTGRAY
                enableLights(true)
            }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(chanel)
        }
    }

}