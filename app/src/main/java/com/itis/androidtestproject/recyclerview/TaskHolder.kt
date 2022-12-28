package com.itis.androidtestproject.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.androidtestproject.data.Task
import com.itis.androidtestproject.databinding.ItemTaskBinding
import com.itis.androidtestproject.databinding.ItemTaskGridBinding
import com.itis.androidtestproject.utils.LayoutType

class TaskHolder(
    private val bindingLinear: ItemTaskBinding?,
    private val bindingGrid: ItemTaskGridBinding?,
    private val onClick: (id: Int) -> Unit,
    private val onDelete: (task: Task) -> Unit
): RecyclerView.ViewHolder(
    bindingLinear?.root ?: bindingGrid!!.root
) {

    fun onBind(task: Task) {
        bindingLinear?.run {
            tvTitle.text = task.title
            tvDescription.text = task.description
            root.setOnClickListener {
                onClick(task.id)
            }
            btnDelete.setOnClickListener {
                onDelete(task)
            }
        }
        bindingGrid?.run {
            tvTitle.text = task.title
            tvDescription.text = task.description
            root.setOnClickListener {
                onClick(task.id)
            }
            btnDelete.setOnClickListener {
                onDelete(task)
            }
        }
    }

    companion object {
        fun create(
            layoutType: LayoutType,
            parent: ViewGroup,
            delete: (task: Task) -> Unit,
            action: (id: Int) -> Unit
        ) = when (layoutType) {
            LayoutType.LINEAR ->
                TaskHolder(
                    bindingLinear = ItemTaskBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    bindingGrid = null,
                    onClick = action,
                    onDelete = delete
                )
            LayoutType.GRID ->
                TaskHolder(
                    bindingLinear = null,
                    bindingGrid = ItemTaskGridBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onClick = action,
                    onDelete = delete
                )
        }
    }
}