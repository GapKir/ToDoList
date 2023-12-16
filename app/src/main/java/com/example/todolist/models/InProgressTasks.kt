package com.example.todolist.models

object InProgressTasks {
    private val inProgressTasks = listOf(
        Task(
            11, "Create layout", "create layout"
        ),
        Task(
            12, "Create View", "create fragment"
        ),
        Task(
            13, "Create ViewModel ", "create fragment`s viewmode"
        ),
        Task(
            14, "Create Model ", "create fragment`s model"
        ),
        Task(
            15, "Create Tests ", "create app tests"
        ),
    )

    fun getTasks(): List<Task>{
        return inProgressTasks
    }

}