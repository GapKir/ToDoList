package com.example.todolist.models

import android.util.Log

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

    override fun getTasks(): List<Task> {
        Log.d("MYTAG", "GET TASKS" + Thread.currentThread().name)
        return doneTasks
    }

    override fun addTask(task: Task){
        Log.d("MYTAG", "ADD TASKS" + Thread.currentThread().name)
        doneTasks.add(task)
    }

}