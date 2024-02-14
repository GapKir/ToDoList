package com.example.todolist.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    @Inject lateinit var notificationHelper: NotificationHelper
    override fun onReceive(context: Context?, intent: Intent?) {
        val title = intent?.getStringExtra(AlarmScheduler.KEY_TITLE) ?: return
        val desc = intent.getStringExtra(AlarmScheduler.KEY_DESC) ?: ""

        if (context != null) {
            notificationHelper.getNotify(NOTIFICATION_ID, title, desc)
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 1
    }
}