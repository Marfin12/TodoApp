package com.example.todoapp.selection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.core.model.Todo
import com.example.todoapp.core.model.TodoList
import com.example.todoapp.todo.TodoListAdapter

/**
 * ViewModel for the selection list screen.
 */
class SelectionModel : ViewModel() {
    private val _index = MutableLiveData(0)
    private val index: LiveData<Int>
        get() = _index

    private val selectionAdapter: SelectionAdapter = SelectionAdapter(ArrayList())

    /**
     * Add todo item and store it to selection adapter temporarily
     *
     * @param todoItem Can be [anyString],
     * @param drawableId Can be [R.id.anyInt]
     */
    fun addTodoItem(todoItem: String, drawableId: Int) {
        index.value?.let { Todo(it,todoItem, drawableId) }?.let { todo ->
            selectionAdapter.addTodoItem(todo)
        }
        _index.value = _index.value?.plus(1)
    }

    /**
     * Get current adapter of todo item adapter for applying its list
     *
     * @return [SelectionAdapter] - Adapter list of selection todo item
     */
    fun getTodoAdapter(): SelectionAdapter {
        return selectionAdapter
    }

    /**
     * Get all todo item list for sending to the submission screen
     *
     * @return [TodoList] - list of commited todo item
     */
    fun getTodoList(): TodoList {
        return selectionAdapter.getTodoList()
    }
}