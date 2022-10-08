package com.example.musicapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.musicapplication.database.DataSong
import com.example.musicapplication.databinding.ActivityMainBinding


/**
 * Created by Bkav TuanTVb on 30/08/2022.
 */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mService: PlaySongService
    private var mBound: Boolean = false
    var listSong = MutableLiveData<ArrayList<DataSong>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    /* Bkav TuanTVb: Định nghĩa callbacks cho service binding, được truyền vào bindService()*/
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            /* Bkav TuanTVb: Liên kết với LocalService,
            truyền IBinder và lấy phiên bản LocalService*/
            val binder = service as PlaySongService.LocalBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onStart() {
        super.onStart()

        /* Bkav TuanTVb: Gọi startService, onStartCommand chứa hàm hiển thị notification*/
        this.startService(
            Intent(this, PlaySongService::class.java)
        )

        /* Bkav TuanTVb: Liên kết client với service*/
        this.bindService(
            Intent(this, PlaySongService::class.java),
            connection,
            Context.BIND_AUTO_CREATE
        )
    }

    override fun onStop() {
        super.onStop()
        /* Bkav TuanTVb: Liên kết client với service*/
        this.unbindService(connection)
        mBound = false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.custom_menu, menu)
        return true
    }

    fun getServiceStatus(): PlaySongService {
        return mService
    }

    fun getBoundStatus(): Boolean {
        return mBound
    }

    companion object {
        const val UPDATE_SONG_UI = "song_update_ui"
        const val DATA = "data_song"
    }
}