package com.example.todolist.alarms

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.example.todolist.App.Companion.NOTIFICATION_CHANNEL_ID
import com.example.todolist.MainActivity
import com.example.todolist.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun getNotify(id: Int, title: String, message: String) {
        val notification = createNotification(title, message)
        val notificationManager = context.getSystemService(NotificationManager::class.java)

        notificationManager.notify(id, notification)
    }

    private fun createNotification(title: String, message: String): Notification {
        val defaultRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val pendingIntent = createPendingIntent()

        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setColor(Color.GREEN)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(defaultRingtone)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun createPendingIntent(): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        return PendingIntent.getActivity(
            context,
            ACTIVITY_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    companion object {
        private const val ACTIVITY_REQUEST_CODE = 0
    }
}