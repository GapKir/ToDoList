package com.example.todolist.base_abstracts

import android.net.Uri

interface DialogListener {
    fun fabClick()
    fun dialogListener(type: BaseScreen.SCREENS, title: String, desc: String?, uri: Uri?)
}

