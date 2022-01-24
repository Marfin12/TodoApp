package com.example.todoapp.todo

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.core.data.source.entity.TodoEntity

/**
 * Adapter for the task list. Has a reference to the [TodoListModel] to send actions back to it.
 */
open class TodoListAdapter(
    private val dataset: List<TodoEntity>
): RecyclerView.Adapter<TodoListAdapter.SubmissionViewHolder>() {

    var onItemClick: ((TodoEntity, Boolean) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubmissionViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_todo, parent, false)

        return SubmissionViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: SubmissionViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = item.todoName
    }

    inner class SubmissionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.item_title)
        val paintFlag = textView.paintFlags

        init {
            var isDefaultTodoStyle = true

            itemView.setOnClickListener {
                if (isDefaultTodoStyle) {
                    textView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    onItemClick?.invoke(dataset[adapterPosition], true)
                }
                else {
                    textView.paintFlags = paintFlag
                    onItemClick?.invoke(dataset[adapterPosition], false)
                }
                isDefaultTodoStyle = !isDefaultTodoStyle
            }
        }
    }

    override fun getItemCount() = dataset.size
}
