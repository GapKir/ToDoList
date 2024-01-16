package com.example.todolist.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ViewModelFactory(
    private val context: Context,
    private val disposableBag: CompositeDisposable? = null
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MainScreenViewModel::class.java)-> {
                MainScreenViewModel(context, disposableBag!!) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java)-> {
                ProfileViewModel(context) as T
            } else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}