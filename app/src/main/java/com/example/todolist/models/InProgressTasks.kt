package com.example.todolist.models

import android.util.Log
import io.reactivex.rxjava3.core.Single

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

    fun getTasks(): Single<List<Task>> {
        Log.d("MYTAG", "GET TASK ${Thread.currentThread().name}")
        return Single.just(inProgressTasks)
    }

    fun addTask(task: Task){
        Log.d("MYTAG", "ADD TASK ${Thread.currentThread().name}")
        inProgressTasks.add(task)
    }

}