package com.example.todolist

import android.app.Application
import androidx.room.Room
import com.example.todolist.database.TasksDatabase

class App : Application() {
    lateinit var taskDatabase: TasksDatabase

    override fun onCreate() {
        super.onCreate()

        taskDatabase = Room.databaseBuilder(
            this,
            TasksDatabase::class.java,
            "tasks_database")
            .build()
    }
}