package com.example.todolist.base_abstracts

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.App
import com.example.todolist.fragments.AddTaskDialog
import com.example.todolist.models.DataBaseRepository
import com.example.todolist.viewmodels.ViewModelFactory

abstract class BaseFragment<VM : ViewModel>(viewModelClass: Class<VM>) : Fragment(), DialogListener {

    private val application = (requireContext().applicationContext) as App
    private val repository = DataBaseRepository(application.taskDatabase.tasksDao())

    protected val viewModel: VM by lazy {
        ViewModelProvider(this, ViewModelFactory(requireContext(), repository))[viewModelClass]
    }
    protected val currentScreenName: TaskCategories by lazy {
        arguments?.getString(KEY_ARG)?.let { TaskCategories.valueOf(it) } ?: TaskCategories.IN_PROGRESS
    }

    override fun fabClick() {
        AddTaskDialog(this).show(parentFragmentManager, null)
    }

    override fun dialogListener(type: TaskCategories, title: String, desc: String?, uri: Uri?) {
    }


    companion object {
        const val KEY_ARG = "KEY_ARG"
        inline fun <reified S : BaseFragment<*>> createInstance(param: TaskCategories? = null): S =
            S::class.java.getDeclaredConstructor().newInstance().apply {
                arguments = Bundle().apply {
                    putString(KEY_ARG, param?.name)
                }
            }


    }
}
