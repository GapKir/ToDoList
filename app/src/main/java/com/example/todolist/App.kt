package com.example.todolist

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.todolist.database.TasksDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    lateinit var taskDatabase: TasksDatabase

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = this.getSystemService(NotificationManager::class.java)

            notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID)
                ?: notificationManager.createNotificationChannel(
                    NotificationChannel(
                        NOTIFICATION_CHANNEL_ID,
                        NOTIFICATION_CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_HIGH
                    )
                )
        }
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "com.example.notifications"
        private const val NOTIFICATION_CHANNEL_NAME = "notifications"
    }
}