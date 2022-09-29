package com.example.musicapplication

import android.app.*
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

/**
 * Created by Bkav TuanTVb on 05/09/2022.
 */

class PlaySongService() : Service() {

    private lateinit var player: MediaPlayer
    private val binder = LocalBinder()

    companion object {
        //không nên đặt const cho 2 biến này. Nên để dạng XML
        const val CHANEL_ID = "chanelID"
        const val CHANEL_NAME = "chanelName"
        const val NOTIF_ID = 0
    }

    override fun onCreate() {
        super.onCreate()
        createNotifChanel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotification()
        //player.start()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
    }

    fun playMusic(path: String) {
        /*Bkav TuanTVb: Xử lý phát nhạc được chọn*/
        player?.let {
            if (player.isPlaying) {
                player.stop()
            }
            player = MediaPlayer.create(applicationContext, Uri.parse(path))
            player.start()
        }
    }

    /* Bkav TuanTVb: Xử lý play/pause bài nhạc*/
    fun playAndPauseMusic() {
        if (player.isPlaying) {
            player.pause()
        } else
            player.start()
    }

    fun getStatusMusic(): Boolean {
        return player.isPlaying
    }

    inner class LocalBinder : Binder() {
        /* Bkav TuanTVb: Kết quả trả về cho phép người dùng gọi ra các
        phương thức được tạo trong Service class*/
        fun getService(): PlaySongService = this@PlaySongService
    }

    override fun onBind(intent: Intent): IBinder? {
        player = MediaPlayer()
        return binder
    }

    /* Bkav TuanTVb: Đăng ký chanel cho Notification*/
    fun createNotifChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chanel = NotificationChannel(
                CHANEL_ID,
                CHANEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = Color.LTGRAY
                enableLights(true)
            }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(chanel)
        }
    }

    /* Bkav TuanTVb: Hiển thị Notification*/
    fun showNotification() {
        val notificationManager = NotificationManagerCompat.from(this)

        val notificationLayout =
            RemoteViews(applicationContext.packageName, R.layout.music_notification)
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(NOTIF_ID, PendingIntent.FLAG_CANCEL_CURRENT)
        }

        /* Bkav TuanTVb: Apply layout cho notification*/
        val customNotification = NotificationCompat.Builder(applicationContext, CHANEL_ID)
            .setSmallIcon(R.drawable.stat_notify_musicplayer)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
        notificationManager.notify(NOTIF_ID,customNotification)
    }
}