package com.example.todoapp.core.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.todoapp.R
import com.example.todoapp.core.constants.*
import com.example.todoapp.core.data.source.room.TodoDatabase
import com.example.todoapp.todo.TodoFragment
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

@VisibleForTesting
class HandleNullableWorkerUtilsException {
    companion object {
        var intent: Intent? = null
        var notification: Notification? = null
        var notificationChannel: NotificationChannel? = null
        var database: TodoDatabase? = null
    }
}

fun makeStatusNotification(title: String, message: String, context: Context) {
    var intent: Intent?
    var notification: Notification?
    var channel: NotificationChannel?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
        val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        try {
            channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
        } catch (err: Exception) {
            channel = HandleNullableWorkerUtilsException.notificationChannel
            channel!!.description = description
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel!!)
    }
    try {
        intent = Intent(context, TodoFragment::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
    } catch (err: Exception) {
        intent = HandleNullableWorkerUtilsException.intent
    }
    val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
    notification = try {
        NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setVibrate(LongArray(0))
            .build()
    } catch (err: Exception) {
        HandleNullableWorkerUtilsException.notification
    }
    if (notification != null) {
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }
}

fun sleep() {
    try {
        Thread.sleep(DELAY_TIME_MILLIS, 0)
    } catch (e: InterruptedException) {
        Log.e(TAG_WORKER_UTILS, e.message!!)
    }
}

