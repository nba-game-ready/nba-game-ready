package com.example.nbagameready.receivers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.example.nbagameready.R
import com.example.nbagameready.util.sendNotification

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context, p1: Intent) {
        val notificationManager = ContextCompat.getSystemService(
            p0,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotification(
            p0.getText(R.string.game_ready).toString(),
            p0
        )
    }
}