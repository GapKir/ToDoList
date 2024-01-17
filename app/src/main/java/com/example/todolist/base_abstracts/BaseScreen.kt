package com.example.todolist.base_abstracts

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.screens.AddTaskDialog
import com.example.todolist.viewmodels.ViewModelFactory

abstract class BaseScreen<VM : ViewModel>(viewModelClass: Class<VM>) : Fragment(), DialogListener {

    protected val viewModel: VM by lazy {
        ViewModelProvider(this, ViewModelFactory(requireContext()))[viewModelClass]
    }
    protected val currentScreenName: SCREENS by lazy {
        arguments?.getString(KEY_ARG)?.let { SCREENS.valueOf(it) } ?: SCREENS.IN_PROGRESS
    }

    override fun fabClick() {
        AddTaskDialog(this).show(parentFragmentManager, null)
    }

    override fun dialogListener(type: SCREENS, title: String, desc: String?, uri: Uri?) {
    }

    enum class SCREENS {
        IN_PROGRESS, DONE, DELETED
    }

    companion object {
        const val KEY_ARG = "KEY_ARG"
        inline fun <reified S : BaseScreen<*>> createInstance(param: SCREENS? = null): S =
            S::class.java.getDeclaredConstructor().newInstance().apply {
                arguments = Bundle().apply {
                    putString(KEY_ARG, param?.name)
                }
            }


    }
}
