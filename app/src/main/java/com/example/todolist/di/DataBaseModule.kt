package com.example.todolist.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todolist.base_abstracts.TaskCategories
import com.example.todolist.database.TasksDao
import com.example.todolist.database.TasksDatabase
import com.example.todolist.models.DataBaseRepository
import com.example.todolist.models.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TasksDatabase {
        return Room.databaseBuilder(
            context,
            TasksDatabase::class.java,
            "tasks_database"
        )
            .addCallback(roomDatabaseCallback)
            .build()
    }

    @Provides
    fun provideDao(database: TasksDatabase): TasksDao{
        return database.tasksDao()
    }

    private val roomDatabaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val sqlString =
                "INSERT INTO tasks_table (category, title, description, photo) VALUES ('${TaskCategories.DELETED}', 'Название задачи', 'Описание задачи', '');"
            db.execSQL(sqlString)
        }
    }
}