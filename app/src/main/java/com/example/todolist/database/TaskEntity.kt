package com.example.todolist.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolist.base_abstracts.TaskCategories

@Entity(tableName = "tasks_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var category: TaskCategories,
    var title: String,
    var description: String?,
    var photo: String?
)