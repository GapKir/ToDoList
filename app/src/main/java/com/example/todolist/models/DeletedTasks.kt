package com.example.todolist.models

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object DeletedTasks: Repository {
    private val deletedTasks = mutableListOf(
        Task(
            1, "Do Nothing", "do nothing", null
        ),
        Task(
            2, "Do Nothing", null, null
        ),
        Task(
            3, "Do Nothing", null, null
        ),
        Task(
            4, "Do Nothing", null, null
        ),
        Task(
            5, "Do Nothing", null, null
        ),
    )

    override suspend fun getTasks(): Flow<Task> = flow {
        for (task in deletedTasks){
            delay(300)
            emit(task)
        }
    }

    override suspend fun getLatestTaskId(): Flow<Long> = flow {
        delay(200)
        emit(deletedTasks.size.toLong() + 1)
    }

    override suspend fun addTask(task: Task){
        delay(500)
        deletedTasks.add(task)
    }

}