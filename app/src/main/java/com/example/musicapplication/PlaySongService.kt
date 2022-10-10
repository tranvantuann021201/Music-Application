package com.example.musicapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.PendingIntent.getActivity
import android.app.Service
import android.content.Context
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
import com.example.musicapplication.database.DataSong
/**
 * Created by Bkav TuanTVb on 05/09/2022.
 */

class PlaySongService() : Service() {

    val intent = Intent(MainActivity.UPDATE_SONG_UI)
    var player: MediaPlayer
    private val binder = LocalBinder()
    private var listSong = arrayListOf<DataSong>()

    companion object {
        var index: Int = -1
        const val CHANEL_ID = "chanelID"
        const val CHANEL_NAME = "chanelName"
        const val NOTIF_ID = 0
        const val ACTION_PAUSE = 1
        const val ACTION_PLAY = 2
    }

    init {
        player = MediaPlayer()
    }
    override fun onCreate() {
        createNotificationChanel()
        super.onCreate()
    }

    fun setListSong(songs: ArrayList<DataSong>){
        listSong.clear()
        listSong.addAll(songs)
    }

    fun getListSong(): ArrayList<DataSong>{
        return listSong
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        if (action != null){
            handleActionMusic(action.toString())
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
    }

    fun playMusic(song: DataSong, context: Context) {
        /*Bkav TuanTVb: Xử lý phát nhạc được chọn*/
        player?.let {
            showNotification(song,context)
            if (player.isPlaying) {
                player.stop()
            }
            player = MediaPlayer.create(applicationContext, Uri.parse(song.data))
            player.start()
//            index = listSong.value!!.indexOf(song)
            intent.putExtra(MainActivity.DATA, song)
            sendBroadcast(intent)
        }
    }

    /* Bkav TuanTVb: Xử lý play/pause bài nhạc*/
    fun onMusic() {
        if (!getStatusMusic()) {
            player.start()
        }
    }

    fun pauseMusic() {
        if(getStatusMusic()) {
            player.pause()
        }
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
        return binder
    }

    /* Bkav TuanTVb: Đăng ký chanel cho Notification*/
    fun createNotificationChanel() {
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
    fun showNotification(song: DataSong, context: Context) {
        val notificationManager = NotificationManagerCompat.from(this)
        val intent = Intent(baseContext, MainActivity::class.java)
        val pendingIntent = getActivity(this,0, intent, FLAG_MUTABLE)

        val remoteViews = RemoteViews(packageName, R.layout.music_notification)
        remoteViews.setImageViewBitmap(R.id.img_music_small, song.getPicture(context))
        remoteViews.setTextViewText(R.id.txv_name_song, song.songName)
        remoteViews.setTextViewText(R.id.txv_artist_song, song.artists)
                //TODO
        /* Bkav TuanTVb: Xử lý sự kiện khi bấm vào nút play/pause trên Notification*/
        if (getStatusMusic()) {
            remoteViews.setOnClickPendingIntent(R.id.img_ntf_play_pause,
                getStatusFromNotification(ACTION_PAUSE))
        } else {
            remoteViews.setOnClickPendingIntent(
                R.id.img_ntf_play_pause,
                getStatusFromNotification(ACTION_PLAY)
            )
        }

        if(getStatusMusic()) {
            remoteViews.setImageViewResource(R.id.img_ntf_play_pause, R.drawable.ic_media_pause_dark)
        }
        else {
            remoteViews.setImageViewResource(R.id.img_ntf_play_pause, R.drawable.ic_media_play_dark)
        }
        /* Bkav TuanTVb: Apply layout cho notification*/
        val customNotification = NotificationCompat.Builder(this, CHANEL_ID)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.stat_notify_musicplayer)
            .setContentIntent(pendingIntent)
            .setCustomContentView(remoteViews)
            .setOngoing(true)
            .build()
        notificationManager.notify(NOTIF_ID,customNotification)
        startForeground(NOTIF_ID, customNotification)
        }

    //TODo
    /**
     * Bkav TuanTVb: su ly su kien tren notification
     */
    private fun handleActionMusic(action: String?) {
        val song: DataSong = listSong[0]
        when (action) {
            ACTION_PAUSE.toString() -> {
                onMusic()
                showNotification(song,applicationContext)
            }
            ACTION_PLAY.toString() -> {
                pauseMusic()
                showNotification(song,applicationContext)
            }
        }
    }

    /**
     * Bkav TuanTVb: chuyen trang thai dang play
     */
    private fun getStatusFromNotification(action: Int): PendingIntent{
        val intentNotification = Intent()
        intentNotification.action = action.toString()
        intentNotification.setClass(this, PlaySongService::class.java)
        return PendingIntent.getService(this, action, intentNotification, PendingIntent.FLAG_IMMUTABLE)
    }
}