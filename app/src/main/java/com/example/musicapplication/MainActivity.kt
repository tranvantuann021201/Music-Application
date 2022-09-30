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
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.musicapplication.database.DataSong
import com.example.musicapplication.databinding.ActivityMainBinding

/**
 * Created by Bkav TuanTVb on 30/08/2022.
 */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    public lateinit var mService: PlaySongService
    public var mBound: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mService = PlaySongService()

        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupWithNavController(binding.navView, navController)


    }


    /* Defines callbacks for service binding, passed to bindService()  */
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
        mBound = false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.custom_menu, menu)
        return true
    }

    companion object {
        var instance: MainActivity? = null
        lateinit var musicListPA : ArrayList<DataSong>
        var songPosition: Int = 0
        public fun getContext(): Context{
            return instance!!.applicationContext
        }
    }
}