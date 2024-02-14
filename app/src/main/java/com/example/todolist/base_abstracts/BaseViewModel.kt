package com.example.todolist.base_abstracts

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.IOException

abstract class BaseViewModel(
    protected  val context: Context
) : ViewModel() {

    protected val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

    protected fun copyImageToInternalFile(imageUri: Uri, fileName: String) {
        try {
                val contentResolver = context.contentResolver
                val file = File(context.filesDir, fileName)
                contentResolver.openInputStream(imageUri)?.use { inputStream ->
                    contentResolver.openOutputStream(Uri.fromFile(file))?.use { outputStream ->
                        val buffer = ByteArray(1024)
                        while (inputStream.read(buffer) != -1) {
                            outputStream.write(buffer)
                        }
                    }
                }
        }catch (e: IOException) {
            e.printStackTrace()
        }
    }
    protected fun readImageFromInternalFile(fileName: String): Uri? {
        return try {
                val file = File(context.filesDir, fileName)
                if (file.exists()) {
                    Uri.fromFile(file)
                } else {
                    null
                }
        }catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }

    companion object {
        const val FILE_PICTURE = "picture_photo.jpg"
        const val FILE_USER = "user_image.jpg"
        private const val SHARED_PREF = "shared_pref"
    }
}