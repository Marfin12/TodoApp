package com.example.todoapp.submission

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.core.model.Todo
import com.example.todoapp.core.model.TodoList
import com.example.todoapp.todo.TodoListModel

/**
 * Adapter for the submission list. Has a reference to the [SubmissionModel] to send actions back to it.
 */
open class SubmissionAdapter(
    private val dataset: ArrayList<Todo>
): RecyclerView.Adapter<SubmissionAdapter.SubmissionViewHolder>() {

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
        holder.textView.text = item.todoItem
    }

    inner class SubmissionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.item_title)
    }

    fun addTodoItem(todo: Todo) {
        dataset.add(todo)
        notifyDataSetChanged()
    }

    fun getTodoList(): TodoList {
        return TodoList(dataset)
    }

    fun clearTodoList() {
        dataset.clear()
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size
}
