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
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainScreenViewModel(
    context: Context,
) : BaseViewModel(context) {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    private val _shouldShowProgress = MutableLiveData<Boolean>()
    val shouldShowProgress: LiveData<Boolean> = _shouldShowProgress

    fun getTasks(screenName: BaseScreen.SCREENS) = viewModelScope.launch(Dispatchers.IO) {
        _shouldShowProgress.postValue(true)
        when (screenName) {
            BaseScreen.SCREENS.IN_PROGRESS -> InProgressTasks.getTasks()
            BaseScreen.SCREENS.DONE -> DoneTasks.getTasks()
            else -> DeletedTasks.getTasks()
        }.collect { task ->
            val currentTasks = _tasks.value.orEmpty().toMutableList()
            if (!currentTasks.contains(task)) {
                currentTasks.add(task)
                _tasks.postValue(currentTasks)
            }
        }
        _shouldShowProgress.postValue(false)
    }

    fun addTask(type: BaseScreen.SCREENS, title: String, desc: String?, uri: Uri? = null, currentScreen: BaseScreen.SCREENS) {
        viewModelScope.launch {
            _shouldShowProgress.postValue(true)
            val latestTaskId = getLatestTask(type)
            val task = Task(latestTaskId, title, desc, uri)
            addTaskByType(type, task)
            if (type == currentScreen){
                getTasks(currentScreen)
            } else {
                _shouldShowProgress.postValue(false)
            }
        }
    }

    private suspend fun getLatestTask(type: BaseScreen.SCREENS): Long =
        withContext(Dispatchers.IO) {
            when (type) {
                BaseScreen.SCREENS.IN_PROGRESS -> InProgressTasks.getLatestTaskId()
                BaseScreen.SCREENS.DONE -> DoneTasks.getLatestTaskId()
                else -> DeletedTasks.getLatestTaskId()
            }.last()
        }

    private suspend fun addTaskByType(type: BaseScreen.SCREENS, task: Task) =
        withContext(Dispatchers.IO) {
            when (type) {
                BaseScreen.SCREENS.IN_PROGRESS -> InProgressTasks.addTask(task)
                BaseScreen.SCREENS.DONE -> DoneTasks.addTask(task)
                else -> DeletedTasks.addTask(task)
            }
        }
}

