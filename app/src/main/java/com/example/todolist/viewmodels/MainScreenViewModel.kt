package com.example.todolist.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist.adapters.TabPagerAdapter
import com.example.todolist.models.DeletedTasks
import com.example.todolist.models.DoneTasks
import com.example.todolist.models.InProgressTasks
import com.example.todolist.models.Task

class MainScreenViewModel(
    context: Context
) : BaseViewModel(context) {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    fun getTasks(screenName: String) {
        _tasks.value = getTasksByType(screenName)
    }

    fun addTask(type: String, title: String, desc: String?, uri: Uri? = null) {
        val taskListId = (getTasksByType(type).size + 1).toLong()
        val task = Task(
            taskListId,
            title,
            desc,
            uri
        )
        addTaskByType(type, task)
    }

    private fun getTasksByType(type: String): List<Task> {
        return when (type) {
            TabPagerAdapter.IN_PROGRESS -> InProgressTasks.getTasks()
            TabPagerAdapter.DONE -> DoneTasks.getTasks()
            else -> DeletedTasks.getTasks()
        }
    }

    private fun addTaskByType(type: String, task: Task) {
        when (type) {
            TabPagerAdapter.IN_PROGRESS -> InProgressTasks.addTask(task)
            TabPagerAdapter.DONE -> DoneTasks.addTask(task)
            else -> DeletedTasks.addTask(task)
        }
    }
}
