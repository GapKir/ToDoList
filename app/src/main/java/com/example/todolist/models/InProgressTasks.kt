package com.example.todolist.models

import android.util.Log

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

    override fun getTasks(): List<Task> {
        Log.d("MYTAG", "GET TASKS" + Thread.currentThread().name)
        return inProgressTasks
    }

    override fun addTask(task: Task){
        Log.d("MYTAG", "ADD TASKS" + Thread.currentThread().name)
        inProgressTasks.add(task)
    }

}