package com.example.todolist.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.R
import com.example.todolist.base_abstracts.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext context: Context
) : BaseViewModel(context) {

    private var _isEditMode = MutableLiveData<Boolean>()
    val isEditMode: LiveData<Boolean> = _isEditMode

    private var _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private var _userPhoto = MutableLiveData<Uri>()
    val userPhoto: LiveData<Uri> = _userPhoto


    init {
        loadFromSharedPref()
        viewModelScope.launch(Dispatchers.IO) {
            super.readImageFromInternalFile(FILE_USER)?.let {
                _userPhoto.postValue(it)
            }
        }
    }

    fun setEditMode(isEditMode: Boolean) {
        _isEditMode.value = isEditMode
        super.sharedPreferences.edit().putBoolean(KEY_EDIT_MODE, isEditMode).apply()
    }

    fun setUserName(userName: String) {
        _userName.value = userName
        super.sharedPreferences.edit().putString(KEY_USER_NAME, userName).apply()
    }

    fun setUserPhoto(userPhoto: Uri) {
        _userPhoto.value = userPhoto
        viewModelScope.launch {
            super.copyImageToInternalFile(userPhoto, FILE_USER)
        }
    }

    private fun loadFromSharedPref() {
        _isEditMode.value = super.sharedPreferences.getBoolean(KEY_EDIT_MODE, true)
        _userName.value = super.sharedPreferences.getString(
            KEY_USER_NAME,
            context.getString(R.string.default_user_name)
        )
    }

    companion object {
        private const val KEY_USER_NAME = "KEY_USER_NAME"
        private const val KEY_EDIT_MODE = "KEY_EDIT_MODE"
    }

}