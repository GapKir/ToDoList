package com.example.todolist.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.adapters.TabPagerAdapter
import com.example.todolist.models.DeletedTasks
import com.example.todolist.models.DoneTasks
import com.example.todolist.models.InProgressTasks
import com.example.todolist.models.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainScreenViewModel(
    context: Context,
) : BaseViewModel(context) {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    fun getTasks(screenName: String) {
        viewModelScope.launch(Dispatchers.Default) {
            Log.d("TAG", "${Thread.currentThread().name}")
            _tasks.postValue(getTasksByType(screenName))
        }
    }

    suspend fun addTask(type: String, title: String, desc: String?, uri: Uri? = null) {
        val tasks = getTasksByType(type)
        val taskListId = (tasks.size + 1).toLong()
        val task = Task(
            taskListId,
            title,
            desc,
            uri
        )
        addTaskByType(type, task)
    }

    private fun getTasksByType(type: String): List<Task> {
        var tasks = emptyList<Task>()
        viewModelScope.launch {
            tasks = when (type) {
                TabPagerAdapter.IN_PROGRESS -> InProgressTasks.getTasks()
                TabPagerAdapter.DONE -> DoneTasks.getTasks()
                else -> DeletedTasks.getTasks()
            }
        }
        return tasks
    }

    private fun addTaskByType(type: String, task: Task) {
        viewModelScope.launch {
            when (type) {
                TabPagerAdapter.IN_PROGRESS -> InProgressTasks.addTask(task)
                TabPagerAdapter.DONE -> DoneTasks.addTask(task)
                else -> DeletedTasks.addTask(task)
            }
        }
    }
}
