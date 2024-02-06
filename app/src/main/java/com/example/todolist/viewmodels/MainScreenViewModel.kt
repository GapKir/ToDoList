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
import com.example.todolist.base_abstracts.BaseFragment
import com.example.todolist.base_abstracts.BaseViewModel
import com.example.todolist.base_abstracts.TaskCategories
import com.example.todolist.database.TaskEntity
import com.example.todolist.models.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainScreenViewModel(
    context: Context,
    private val repository: Repository
) : BaseViewModel(context) {

    private val _tasks = MutableLiveData<List<TaskEntity>>()
    val tasks: LiveData<List<TaskEntity>> = _tasks

    private val _shouldShowProgress = MutableLiveData<Boolean>()
    val shouldShowProgress: LiveData<Boolean> = _shouldShowProgress

    fun getTasks(screenName: TaskCategories) = viewModelScope.launch(Dispatchers.IO) {
        _shouldShowProgress.postValue(true)
        repository.getTasksByCategory(screenName).collect { task ->
            val currentTasks = _tasks.value.orEmpty().toMutableList()
            if (!currentTasks.contains(task)) {
                currentTasks.add(task)
                _tasks.postValue(currentTasks)
            }
        }
        _shouldShowProgress.postValue(false)
    }

    fun addTask(type: TaskCategories, title: String, desc: String?, uri: Uri? = null, currentScreen: TaskCategories) {
        viewModelScope.launch {
            _shouldShowProgress.postValue(true)
            val task = TaskEntity(category = type, title = title, description = desc, photo = uri)
            repository.addTask(task)
            if (type == currentScreen){
                getTasks(currentScreen)
            } else {
                _shouldShowProgress.postValue(false)
            }
        }
    }
}

