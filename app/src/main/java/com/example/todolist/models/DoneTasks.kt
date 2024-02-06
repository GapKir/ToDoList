package com.example.todolist.models

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object DoneTasks: Repository {
    private val doneTasks = mutableListOf(
        Task(
            1, "Create Manifest", "create manifest file", null
        ),
        Task(
            2, "Configure Build Gradle", null, null
        ),
        Task(
            3, "Configure Emulator", null, null
        ),
        Task(
            4, "Change SDK Version ", null, null
        ),
        Task(
            5, "Configure Git", null, null
        ),
    )

    override suspend fun getTasksByCategory(): Flow<Task> = flow {
        for (task in doneTasks){
            delay(300)
            emit(task)
        }
    }

    override suspend fun getLatestTaskId(): Flow<Long> = flow {
        delay(200)
        emit(doneTasks.size.toLong() + 1)
    }
    override suspend fun addTask(task: Task){
        delay(500)
        doneTasks.add(task)
    }

}