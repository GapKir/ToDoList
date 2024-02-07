package com.example.todolist.fragments

import android.app.TimePickerDialog
import android.content.Context
import java.util.Calendar

class TimePickDialog(
    context: Context,
    timeListener: (Long?) -> Unit
) : TimePickerDialog(
    context, {_, hourOfDay, minuteOfDay->
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minuteOfDay)
        }
        timeListener.invoke(calendar.timeInMillis)
    },
    getCurrentHour(),
    getCurrentMinute(),
    true
) {
    companion object {

        private val calendar = Calendar.getInstance()
        private fun getCurrentHour(): Int {
            return calendar.get(Calendar.HOUR_OF_DAY)
        }

        private fun getCurrentMinute(): Int {
            return calendar.get(Calendar.MINUTE)
        }
    }
}