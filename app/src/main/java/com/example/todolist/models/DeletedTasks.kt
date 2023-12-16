package com.example.todolist.models

object DeletedTasks {
    private val deletedTasks = listOf(
        Task(
            6, "Do Nothing", "do nothing"
        ),
        Task(
            7, "Do Nothing", null
        ),
        Task(
            8, "Do Nothing", null
        ),
        Task(
            9, "Do Nothing", null
        ),
        Task(
            10, "Do Nothing", null
        ),
    )

    fun getTasks(): List<Task>{
        return deletedTasks
    }

}