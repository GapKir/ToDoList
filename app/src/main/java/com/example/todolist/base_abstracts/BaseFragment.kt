package com.example.todolist.base_abstracts

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.todolist.fragments.AddTaskDialog

abstract class BaseFragment : Fragment(),
    DialogListener {
    protected abstract val viewModel: BaseViewModel

    protected val currentScreenName: TaskCategories by lazy {
        arguments?.getString(KEY_ARG)?.let { TaskCategories.valueOf(it) }
            ?: TaskCategories.IN_PROGRESS
    }

    override fun fabClick() {
        AddTaskDialog(this).show(parentFragmentManager, null)
    }

    override fun dialogListener(type: TaskCategories, title: String, desc: String?, uri: String?) {
    }

    companion object {
        const val KEY_ARG = "KEY_ARG"
        inline fun <reified S : BaseFragment> createInstance(param: TaskCategories? = null): S =
            S::class.java.getDeclaredConstructor().newInstance().apply {
                arguments = Bundle().apply {
                    putString(KEY_ARG, param?.name)
                }
            }
    }
}
