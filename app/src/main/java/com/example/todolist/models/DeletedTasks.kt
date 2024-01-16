package com.example.todolist.models

import io.reactivex.rxjava3.core.Single

object DeletedTasks {
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

    fun getTasks(): Single<List<Task>>{
        return Single.just(deletedTasks)
    }

    fun addTask(task: Task){
        deletedTasks.add(task)
    }

}