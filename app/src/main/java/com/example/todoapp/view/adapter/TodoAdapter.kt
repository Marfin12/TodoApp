package com.example.todoapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.model.data.Todo
import com.example.todoapp.model.data.TodoList

@Suppress("UNCHECKED_CAST")
open class TodoAdapter(
    private val dataset: ArrayList<Todo>
): RecyclerView.Adapter<TodoAdapter.ItemViewHolder>() {


    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_todo, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
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

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size

    fun addTodoItem(todo: Todo) {
        dataset.add(todo)
        notifyDataSetChanged()
    }

    fun getTodoList(): TodoList {
        return TodoList(dataset)
    }
}
