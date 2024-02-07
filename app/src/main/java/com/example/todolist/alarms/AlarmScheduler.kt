package com.example.todolist.alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class AlarmScheduler(
    private val context: Context
) {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    fun schedule(title: String, desc: String, time: Long) {
        try {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                time,
                createIntent(title, desc)
            )
        } catch (e: SecurityException){
            e.printStackTrace()
        }
    }

    private fun createIntent(title: String, desc: String): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            this.putExtra(KEY_TITLE, title)
            this.putExtra(KEY_DESC, desc)
        }
        return PendingIntent.getBroadcast(
            context,
            BROADCAST_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    companion object {
        const val KEY_TITLE = "KEY_TITLE"
        const val KEY_DESC = "KEY_DESC"
        private const val BROADCAST_REQUEST_CODE = 1
    }
}