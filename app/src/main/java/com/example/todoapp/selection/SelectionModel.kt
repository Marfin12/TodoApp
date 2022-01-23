package com.example.todoapp.selection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.core.model.Todo
import com.example.todoapp.core.model.TodoList

class SelectionModel : ViewModel() {
    private val _index = MutableLiveData(0)
    private val index: LiveData<Int>
        get() = _index

    private val selectionAdapter: SelectionAdapter = SelectionAdapter(ArrayList())

    fun addTodoItem(todoItem: String, drawableId: Int) {
        index.value?.let { Todo(it,todoItem, drawableId) }?.let { todo ->
            selectionAdapter.addTodoItem(todo)
        }
        _index.value = _index.value?.plus(1)
    }

    fun getTodoAdapter(): SelectionAdapter {
        return selectionAdapter
    }

    fun getTodoList(): TodoList {
        return selectionAdapter.getTodoList()
    }
}