package com.example.todolist

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todolist.base_abstracts.TaskCategories
import com.example.todolist.database.TasksDatabase

class App : Application() {
    lateinit var taskDatabase: TasksDatabase

    override fun onCreate() {
        super.onCreate()
        taskDatabase = Room.databaseBuilder(
            this,
            TasksDatabase::class.java,
            "tasks_database")
            .addCallback(roomDatabaseCallback)
            .build()
    }

    private val roomDatabaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val sqlString = "INSERT INTO tasks_table (category, title, description, photo) VALUES ('${TaskCategories.DELETED}', 'Название задачи', 'Описание задачи', '');"
            db.execSQL(sqlString)
        }
    }
}