package com.example.todolist.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.models.DeletedTasks
import com.example.todolist.models.DoneTasks
import com.example.todolist.models.InProgressTasks
import com.example.todolist.models.Task
import com.example.todolist.base_abstracts.BaseScreen
import com.example.todolist.base_abstracts.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainScreenViewModel(
    context: Context,
) : BaseViewModel(context) {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    fun getTasks(screenName: BaseScreen.SCREENS) {
        viewModelScope.launch(Dispatchers.IO) {
            _tasks.postValue(getTasksByType(screenName))
        }
    }

    fun addTask(type: BaseScreen.SCREENS, title: String, desc: String?, uri: Uri? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val tasks = getTasksByType(type)
            val taskListId = (tasks.size + 1).toLong()
            val task = Task(taskListId, title, desc, uri)

            addTaskByType(type, task)
        }
    }

    private fun getTasksByType(type: BaseScreen.SCREENS): List<Task> {
        return when (type) {
                BaseScreen.SCREENS.IN_PROGRESS -> InProgressTasks.getTasks()
                BaseScreen.SCREENS.DONE -> DoneTasks.getTasks()
                else -> DeletedTasks.getTasks()
            }
        }

    private fun addTaskByType(type: BaseScreen.SCREENS, task: Task) {
            when (type) {
                BaseScreen.SCREENS.IN_PROGRESS -> InProgressTasks.addTask(task)
                BaseScreen.SCREENS.DONE -> DoneTasks.addTask(task)
                else -> DeletedTasks.addTask(task)
            }
    }
}
