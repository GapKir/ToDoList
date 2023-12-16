package com.example.todolist.models

object DoneTasks {
    private val doneTasks = listOf(
        Task(
            1, "Create Manifest", "create manifest file"
        ),
        Task(
            2, "Configure Build Gradle", null
        ),
        Task(
            3, "Configure Emulator", null
        ),
        Task(
            4, "Change SDK Version ", null
        ),
        Task(
            5, "Configure Git", null
        ),
    )

    fun getTasks(): List<Task>{
        return doneTasks
    }

}