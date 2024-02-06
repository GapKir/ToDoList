package com.example.todolist.models

import com.example.todolist.base_abstracts.TaskCategories
import com.example.todolist.database.TaskEntity
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getTasksByCategory(category: TaskCategories): Flow<List<TaskEntity>>
    suspend fun addTask(task: TaskEntity)
}