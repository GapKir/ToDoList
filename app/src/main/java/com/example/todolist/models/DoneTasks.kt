package com.example.todolist.models

import io.reactivex.rxjava3.core.Single

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

    fun getTasks(): Single<List<Task>> {
        return Single.just(doneTasks)
    }

    fun addTask(task: Task){
        doneTasks.add(task)
    }

}