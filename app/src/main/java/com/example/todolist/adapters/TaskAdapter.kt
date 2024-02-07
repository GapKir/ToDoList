package com.example.todolist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.database.TaskEntity
import com.example.todolist.databinding.ItemTaskBinding

class TaskAdapter(
    private var tasks: List<TaskEntity>
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    fun updateData(newTasksList: List<TaskEntity>){
        val diffCallback = TaskDiffCallback(tasks, newTasksList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        tasks = newTasksList
        diffResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)

        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    class TaskViewHolder(
        private val binding: ItemTaskBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(task: TaskEntity){
            with(binding) {
                taskId.text = task.id.toString()
                taskTitle.text = task.title
                taskDesc.text = task.description
            }
        }

    }
}