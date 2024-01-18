package com.example.todolist.models

interface Repository {
    fun getTasks(): List<Task>
    fun addTask(task: Task)
}