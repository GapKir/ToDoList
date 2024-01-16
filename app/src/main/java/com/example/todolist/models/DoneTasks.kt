package com.example.todolist.models

object DoneTasks {
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

    fun getTasks(): List<Task> {
        return doneTasks
    }

    fun addTask(task: Task){
        doneTasks.add(task)
    }

}