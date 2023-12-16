package com.example.todolist.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class ProfileViewModel(
   private val context: Context
): ViewModel() {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

    private var _isEditMode = MutableLiveData<Boolean>()
    val isEditMode: LiveData<Boolean> = _isEditMode

    private var _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private var _userPhoto = MutableLiveData<Uri>()
    val userPhoto: LiveData<Uri> = _userPhoto


    init {
        loadFromSharedPref()
        readImageFromFile()?.let {
            _userPhoto.value = it
        }
    }

    fun setEditMode(isEditMode: Boolean) {
        _isEditMode.value = isEditMode
        sharedPreferences.edit().putBoolean(KEY_EDIT_MODE, isEditMode).apply()
    }

    fun setUserName(userName: String){
        _userName.value = userName
        sharedPreferences.edit().putString(KEY_USER_NAME, userName).apply()
    }

    fun setUserPhoto(userPhoto: Uri){
        _userPhoto.value = userPhoto
        copyImageToFile(userPhoto)
    }

    private fun loadFromSharedPref(){
        _isEditMode.value = sharedPreferences.getBoolean(KEY_EDIT_MODE, true)
        _userName.value = sharedPreferences.getString(KEY_USER_NAME, context.getString(R.string.default_user_name))
    }

    private fun copyImageToFile(imageUri: Uri) {
        try {
            val inputStream: InputStream? =
                context.contentResolver.openInputStream(imageUri)

            val internalStoragePath = context.filesDir
            val file = File(internalStoragePath, FILE_NAME)

            val outputStream: OutputStream = FileOutputStream(file)

            if (inputStream != null) {
                val buffer = ByteArray(1024)
                while (inputStream.read(buffer) != -1) {
                    outputStream.write(buffer)
                }
                inputStream.close()
            }
            outputStream.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun readImageFromFile(): Uri? {
        return try {
            val file = File(context.filesDir, FILE_NAME)
            if (file.exists()) {
                Uri.fromFile(file)
            } else {
                null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }


    companion object{
        private const val FILE_NAME = "file_name.jpg"
        private const val SHARED_PREF = "shared_pref"
        private const val KEY_USER_NAME = "KEY_USER_NAME"
        private const val KEY_EDIT_MODE = "KEY_EDIT_MODE"
    }

}