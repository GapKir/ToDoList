package com.example.todolist.models

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object InProgressTasks: Repository{
    private val inProgressTasks = mutableListOf(
        Task(
            1, "Create layout", "create layout", null
        ),
        Task(
            2, "Create View", "create fragment", null
        ),
        Task(
            3, "Create ViewModel ", "create fragment`s viewmode", null
        ),
        Task(
            4, "Create Model ", "create fragment`s model", null
        ),
        Task(
            5, "Create Tests ", "create app tests", null
        ),
    )

    override suspend fun getTasksByCategory(): Flow<Task> = flow {
        for (task in inProgressTasks){
            delay(300)
            emit(task)
        }
    }

    override suspend fun getLatestTaskId(): Flow<Long> = flow {
        delay(200)
        emit(inProgressTasks.size.toLong() + 1)
    }

    override suspend fun addTask(task: Task){
        delay(500)
        inProgressTasks.add(task)
    }

}