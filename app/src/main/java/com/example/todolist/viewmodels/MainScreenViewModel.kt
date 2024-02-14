package com.example.todolist.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.base_abstracts.BaseViewModel
import com.example.todolist.base_abstracts.TaskCategories
import com.example.todolist.database.TaskEntity
import com.example.todolist.models.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val repository: Repository
) : BaseViewModel(context) {

    private val dispatcher = Dispatchers.IO

    private val _tasks = MutableLiveData<List<TaskEntity>>()
    val tasks: LiveData<List<TaskEntity>> = _tasks

    private val _shouldShowProgress = MutableLiveData<Boolean>()
    val shouldShowProgress: LiveData<Boolean> = _shouldShowProgress

    fun getTasks(screenName: TaskCategories) = viewModelScope.launch(dispatcher) {
        _shouldShowProgress.postValue(true)
        repository.getTasksByCategory(screenName).collect { tasks ->
            var currentTasks = _tasks.value.orEmpty().toMutableList()
            if (currentTasks != tasks) {
                currentTasks = tasks.toMutableList()
                _tasks.postValue(currentTasks)
            }
            _shouldShowProgress.postValue(false)
        }
    }

    fun addTask(type: TaskCategories, title: String, desc: String?, uri: String? = null, currentScreen: TaskCategories) {
        viewModelScope.launch(dispatcher) {
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

