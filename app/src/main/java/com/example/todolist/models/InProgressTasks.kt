package com.example.todolist.models

object InProgressTasks {
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

    fun getTasks(): List<Task> {
        return inProgressTasks
    }

    fun addTask(task: Task){
        inProgressTasks.add(task)
    }

}