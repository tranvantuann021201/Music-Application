package com.example.musicapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by Bkav TuanTVb on 05/10/2022.
 */
class BroadcastReceiverNotification: BroadcastReceiver() {
    private val ACTION_NOTIFICATION = "action_notification"
    override fun onReceive(context: Context?, intent: Intent?) {
        val action: Int = intent!!.getIntExtra(ACTION_NOTIFICATION, 0)
        val intentAction = Intent(context, PlaySongService::class.java)
        intentAction.putExtra(ACTION_NOTIFICATION, action)
        context?.startService(intent)
    }
}