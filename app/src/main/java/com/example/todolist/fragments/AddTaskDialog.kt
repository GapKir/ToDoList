package com.example.todolist.fragments

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
import androidx.lifecycle.lifecycleScope
import com.example.todolist.R
import com.example.todolist.alarms.AlarmScheduler
import com.example.todolist.base_abstracts.DialogListener
import com.example.todolist.databinding.DialogAddTaskBinding
import com.example.todolist.base_abstracts.BaseViewModel
import com.example.todolist.base_abstracts.TaskCategories
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class AddTaskDialog(
    private val listener: DialogListener
) : DialogFragment() {

    private lateinit var dialogBinding: DialogAddTaskBinding
    private var fileUri: Uri? = null
    private var pickedTime: Long? = null
    @Inject lateinit var alarmScheduler: AlarmScheduler

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { imageGalleryUri ->
        imageGalleryUri?.let {
            lifecycleScope.launch {
                copyImageToPath(it, fileUri!!)
            }

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
                lifecycleScope.launch {
                    copyImageToPath(currentImageUri, imageUriGallery)
                }
            }
        }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialogBinding = DialogAddTaskBinding.inflate(layoutInflater)

        initListenersOnView()

        val dialog = AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setTitle(R.string.create_task_title)
            .setMessage(R.string.create_task_message)
            .setNegativeButton(R.string.close) { _, _ -> dismiss() }
            .setPositiveButton(R.string.confirm) { _, _ -> setPositiveButton() }
            .setView(dialogBinding.root)
            .create()
        dialog.setCanceledOnTouchOutside(false)

        return dialog

    }

    private fun setPositiveButton() {
        val taskTitle = dialogBinding.createTaskTitle.text.toString()
        val taskDescription = dialogBinding.createTaskDescription.text.toString()
        val taskType = when (dialogBinding.radioGroup.checkedRadioButtonId) {
            R.id.button_in_progress -> TaskCategories.IN_PROGRESS
            R.id.button_done -> TaskCategories.DONE
            else -> TaskCategories.DELETED
        }

        if (dialogBinding.createTaskTitle.text.isNotBlank()) {
            listener.dialogListener(taskType, taskTitle, taskDescription, fileUri.toString())

            createScheduleAlarm(taskTitle, taskDescription)
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

        dialogBinding.buttonPickTime.setOnClickListener {
            (TimePickDialog(requireContext()) { pickedTime = it }).show()
        }

        dialogBinding.root.setOnClickListener {
            hideKeyboard(it)
        }
    }

    private fun createScheduleAlarm(title: String, desc: String) {
        pickedTime?.let {time->
            alarmScheduler.schedule(
                title, desc, time
            )
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