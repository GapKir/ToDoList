package com.example.todolist.models

import android.util.Log

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

    override fun getTasks():List<Task>{
        Log.d("MYTAG", "GET TASKS" + Thread.currentThread().name)
        return deletedTasks
    }

    override fun addTask(task: Task){
        Log.d("MYTAG", "ADD TASKS" + Thread.currentThread().name)
        deletedTasks.add(task)
    }

}