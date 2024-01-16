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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainScreenViewModel(
    context: Context,
    private val disposableBag: CompositeDisposable
) : BaseViewModel(context) {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    fun getTasks(screenName: String) {
        val disposable = getTasksByType(screenName)
            .subscribe({ tasks ->
                _tasks.value = tasks
            }, { error ->
                throw Exception(error)
            })
        disposableBag.add(disposable)
    }

    fun addTask(type: String, title: String, desc: String?, uri: Uri? = null) {
        val disposable = getTasksByType(type)
            .subscribe({ tasks ->
                val taskListId = (tasks.size + 1).toLong()
                val task = Task(
                    taskListId,
                    title,
                    desc,
                    uri
                )
                addTaskByType(type, task)
            }, { error ->
                throw Exception(error)
            })
        disposableBag.add(disposable)
    }

    private fun getTasksByType(type: String): Single<List<Task>> {
        return when (type) {
            TabPagerAdapter.IN_PROGRESS -> InProgressTasks.getTasks()
            TabPagerAdapter.DONE -> DoneTasks.getTasks()
            else -> DeletedTasks.getTasks()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun addTaskByType(type: String, task: Task) {
        val completable = when (type) {
            TabPagerAdapter.IN_PROGRESS -> Completable.fromCallable { InProgressTasks.addTask(task) }
            TabPagerAdapter.DONE -> Completable.fromCallable { DoneTasks.addTask(task) }
            else -> Completable.fromCallable { DeletedTasks.addTask(task) }
        }
            .subscribeOn(Schedulers.io())
            .subscribe({
            }, { error ->
                throw Exception(error)
            })
        disposableBag.add(completable)
    }
}
