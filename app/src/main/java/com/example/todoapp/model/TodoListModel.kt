package com.example.todoapp.model

import androidx.lifecycle.*
import com.example.todoapp.adapter.TodoListAdapter
import com.example.todoapp.data.TodoDAO
import com.example.todoapp.data.TodoData
import kotlinx.coroutines.launch

class TodoListModel(private val todoDAO: TodoDAO) : ViewModel() {

    val allTodoData: LiveData<List<TodoData>> = todoDAO.getTodoList().asLiveData()
    private var todoListAdapter: TodoListAdapter = TodoListAdapter(listOf())

    fun submitItem(todos: TodoList) {
        viewModelScope.launch {
            println("todos")
            println(todos)
            todos.todoList.forEach { todo ->
                todoDAO.insert(TodoData(todo.id, todo.todoItem))
            }
        }
    }

    fun updateTodoList(todoDataList: List<TodoData>) {
        println("gaa")
        println(todoDataList)
        todoListAdapter = TodoListAdapter(todoDataList)
    }

    fun getCurrentAdapter(): TodoListAdapter {
        return todoListAdapter
    }

    class TodoListViewModelFactory(private val todoDAO: TodoDAO) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TodoListModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TodoListModel(todoDAO) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}