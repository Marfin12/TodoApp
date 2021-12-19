package com.example.todoapp.view.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.model.data.TodoData

open class TodoListAdapter(
    private val dataset: List<TodoData>
): RecyclerView.Adapter<TodoListAdapter.SubmissionViewHolder>() {

    var onItemClick: ((TodoData, Boolean) -> Unit)? = null

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubmissionViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_todo, parent, false)

        return SubmissionViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
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

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size
}
