package com.example.todolist.screens

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import com.example.todolist.R
import com.example.todolist.adapters.TabPagerAdapter
import com.example.todolist.databinding.DialogAddTaskBinding
import com.example.todolist.viewmodels.BaseViewModel
import java.io.File
import java.io.IOException

class AddTaskDialog(
    private val listener: DialogListener
) : DialogFragment() {

    private lateinit var dialogBinding: DialogAddTaskBinding
    private var fileUri: Uri? = null

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { imageGalleryUri ->
        imageGalleryUri?.let {
            copyImageToPath(it, fileUri!!)

            with(dialogBinding.createTaskImage) {
                visibility = View.VISIBLE
                setImageURI(it)
            }
        }
    }

    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            val currentImageUri = fileUri
            if (it != true) return@registerForActivityResult

            with(dialogBinding.createTaskImage) {
                visibility = View.VISIBLE
                setImageURI(fileUri)
            }

            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "Image_${System.currentTimeMillis()}.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }

            val imageUriGallery = requireContext().contentResolver
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            if (imageUriGallery != null && currentImageUri != null) {
                copyImageToPath(currentImageUri, imageUriGallery)
            }
        }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialogBinding = DialogAddTaskBinding.inflate(layoutInflater)

        initListenersOnView()

        return AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setTitle(R.string.create_task_title)
            .setMessage(R.string.create_task_message)
            .setNegativeButton(R.string.close) { _, _ -> dismiss() }
            .setPositiveButton(R.string.confirm) { _, _ -> setPositiveButton() }
            .setView(dialogBinding.root)
            .create()
    }

    private fun setPositiveButton() {
        val taskTitle = dialogBinding.createTaskTitle.text.toString()
        val taskDescription = dialogBinding.createTaskDescription.text.toString()
        val taskType = when (dialogBinding.radioGroup.checkedRadioButtonId) {
            R.id.button_in_progress -> TabPagerAdapter.IN_PROGRESS
            R.id.button_done -> TabPagerAdapter.DONE
            else -> TabPagerAdapter.DELETED
        }
        if (dialogBinding.createTaskTitle.text.isNotBlank()) {
            listener(taskType, taskTitle, taskDescription, fileUri)
        }
        dismiss()
    }

    private fun initListenersOnView() {
        dialogBinding.buttonOpenGallery.setOnClickListener {
            if (fileUri == null) fileUri = createUriForFile()
            galleryLauncher.launch("image/*")
        }

        dialogBinding.buttonTakePicture.setOnClickListener {
            if (fileUri == null) fileUri = createUriForFile()
            takePictureLauncher.launch(fileUri)
        }

        dialogBinding.root.setOnClickListener {
            hideKeyboard(it)
        }
    }

    private fun hideKeyboard(view: View) {
        val imm =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun copyImageToPath(imageUri: Uri, copyToPath: Uri) {
        try {
            val contentResolver = requireContext().contentResolver
            contentResolver.openInputStream(imageUri)?.use { inputStream ->
                contentResolver.openOutputStream(copyToPath)?.use { outputStream ->
                    val buffer = ByteArray(1024)
                    while (inputStream.read(buffer) != -1) {
                        outputStream.write(buffer)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun createUriForFile(): Uri {
        val imageFile = File(requireContext().filesDir, BaseViewModel.FILE_PICTURE)

        return FileProvider.getUriForFile(
            requireContext(),
            getString(R.string.authorities),
            imageFile
        )
    }
}