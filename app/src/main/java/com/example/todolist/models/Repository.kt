package com.example.todolist.models

import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getTasks(): Flow<Task>
   suspend fun getLatestTaskId(): Flow<Long>
    suspend fun addTask(task: Task)
}