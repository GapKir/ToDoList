package com.example.todolist.models

import com.example.todolist.base_abstracts.TaskCategories
import com.example.todolist.database.TaskEntity
import com.example.todolist.database.TasksDao
import kotlinx.coroutines.flow.Flow

class DataBaseRepository(
    private val tasksDao: TasksDao
): Repository {

    override suspend fun getTasksByCategory(category: TaskCategories): Flow<List<TaskEntity>> {
        return tasksDao.getTasks(category)
    }

    override suspend fun addTask(task: TaskEntity) {
        tasksDao.addTask(task)
    }
}