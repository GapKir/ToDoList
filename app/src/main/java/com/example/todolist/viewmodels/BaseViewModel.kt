package com.example.todolist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.adapters.TabPagerAdapter
import com.example.todolist.models.DeletedTasks
import com.example.todolist.models.DoneTasks
import com.example.todolist.models.InProgressTasks
import com.example.todolist.models.Task

class BaseViewModel() : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks


    fun getTasks(screenName: String) {
        _tasks.value = when (screenName) {
            TabPagerAdapter.IN_PROGRESS -> InProgressTasks.getTasks()
            TabPagerAdapter.DONE -> DoneTasks.getTasks()
            else -> DeletedTasks.getTasks()
        }

    }
}