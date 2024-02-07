package com.example.todolist.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.todolist.database.TaskEntity

class TaskDiffCallback(
    private val oldList: List<TaskEntity>,
    private val newList: List<TaskEntity>,
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}