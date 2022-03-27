package com.example.nbagameready.util

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.example.nbagameready.R
import com.example.nbagameready.ui.MainActivity


private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {

    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val ballImage = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.ball2
    )
    val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(ballImage)
        .bigLargeIcon(null)

    val snoozeIntent = Intent(applicationContext, SnoozeReceiver::class.java)
    val snoozePendingIntent: PendingIntent = PendingIntent.getBroadcast(
        applicationContext,
        REQUEST_CODE,
        snoozeIntent,
        FLAGS
    )
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.team_notifications_id)

    )
        .setSmallIcon(R.drawable.ball2)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setLargeIcon(ballImage)
        .addAction(
            R.drawable.ball2,
            applicationContext.getString(R.string.snooze),
            snoozePendingIntent
        )
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    notify(NOTIFICATION_ID, builder.build())

    fun NotificationManager.cancelNotifications(){
        cancelAll()
    }

}