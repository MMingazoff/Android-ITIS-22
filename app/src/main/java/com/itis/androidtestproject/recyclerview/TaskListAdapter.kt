package com.itis.androidtestproject.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.itis.androidtestproject.data.Task
import com.itis.androidtestproject.utils.LayoutType


class TaskListAdapter(
    private val delete: (task: Task) -> Unit,
    private val action: (id: Int) -> Unit
): ListAdapter<Task, TaskHolder>(
    object : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(
            oldItem: Task,
            newItem: Task
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Task,
            newItem: Task
        ): Boolean = oldItem == newItem
    }
) {
    var layoutType = LayoutType.LINEAR

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskHolder = TaskHolder.create(layoutType, parent, delete, action)

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.onBind(currentList[position])
    }
}