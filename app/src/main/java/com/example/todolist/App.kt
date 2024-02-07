package com.example.todolist

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todolist.alarms.NotificationHelper
import com.example.todolist.base_abstracts.TaskCategories
import com.example.todolist.database.TasksDatabase

class App : Application() {
    lateinit var taskDatabase: TasksDatabase

    override fun onCreate() {
        super.onCreate()
        createDatabase()
        createNotificationChannel()
    }

    private fun createDatabase() {
        taskDatabase = Room.databaseBuilder(
            this,
            TasksDatabase::class.java,
            "tasks_database"
        )
            .addCallback(roomDatabaseCallback)
            .build()
    }

    private val roomDatabaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val sqlString =
                "INSERT INTO tasks_table (category, title, description, photo) VALUES ('${TaskCategories.DELETED}', 'Название задачи', 'Описание задачи', '');"
            db.execSQL(sqlString)
        }
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