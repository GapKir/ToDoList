package com.example.todolist.models

import android.net.Uri

data class Task(
    val id: Long,
    var title: String,
    var description: String?,
    var photo: Uri?
)
