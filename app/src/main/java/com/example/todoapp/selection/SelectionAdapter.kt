package com.example.todoapp.selection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.core.model.Todo
import com.example.todoapp.core.model.TodoList
import com.example.todoapp.submission.SubmissionModel

/**
 * Adapter for the todo list. Has a reference to the [SelectionModel] to send actions back to it.
 */
@Suppress("UNCHECKED_CAST")
open class SelectionAdapter(
    private val dataset: ArrayList<Todo>
): RecyclerView.Adapter<SelectionAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_todo, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]

        holder.textView.text = item.todoItem
        holder.imageView.setImageResource(item.imageResourceId)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.item_title)
        val imageView: ImageView = itemView.findViewById(R.id.item_image)

        init {
            imageView.setOnClickListener {
                dataset.removeAt(adapterPosition)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount() = dataset.size

    fun addTodoItem(todo: Todo) {
        dataset.add(todo)
        notifyDataSetChanged()
    }

    fun getTodoList(): TodoList {
        return TodoList(dataset)
    }
}
