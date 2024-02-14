package com.example.todolist.models

import com.example.todolist.base_abstracts.TaskCategories
import com.example.todolist.database.TaskEntity
import com.example.todolist.database.TasksDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataBaseRepository @Inject constructor(
    private val tasksDao: TasksDao
) : Repository {

    override suspend fun getTasksByCategory(category: TaskCategories): Flow<List<TaskEntity>> {
        delay(1000)
        return tasksDao.getTasks(category)
    }
    override suspend fun addTask(task: TaskEntity) {
        delay(1000)
        tasksDao.addTask(task)
    }
}