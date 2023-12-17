package com.example.todolist.models

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

    fun getTasks(): List<Task>{
        return deletedTasks
    }

    fun addTask(task: Task){
        deletedTasks.add(task)
    }

}