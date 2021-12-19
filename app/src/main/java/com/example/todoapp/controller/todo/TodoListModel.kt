package com.example.todoapp.controller.todo

import androidx.lifecycle.*
import com.example.todoapp.model.data.TodoDAO
import com.example.todoapp.model.data.TodoData
import com.example.todoapp.model.data.TodoList
import com.example.todoapp.view.adapter.TodoListAdapter
import kotlinx.coroutines.launch

class TodoListModel(private val todoDAO: TodoDAO) : ViewModel() {

    val allTodoData: LiveData<List<TodoData>> = todoDAO.getTodoList().asLiveData()
    private var todoListAdapter: TodoListAdapter = TodoListAdapter(listOf())

    fun submitItem(todos: TodoList, action: () -> Unit) {
        viewModelScope.launch {
            todos.todoList.forEach { todo ->
                todoDAO.insert(TodoData(todoName = todo.todoItem))
            }
        }
        action()
    }

    fun removeItem(todos: String) {
        try {
            viewModelScope.launch {
                println(todos)
                todoDAO.delete(TodoData(id = 1, todoName = todos))
            }
        } catch (exception: Exception) {
            println(exception.toString())
        }
    }

    fun updateTodoList(todoDataList: List<TodoData>, workerModel: TodoWorkerModel) {
        todoListAdapter = TodoListAdapter(todoDataList)
        todoDataList.forEach {
            todoListAdapter.onItemClick = {todoData, isStriked ->
                println("clicked")
                println(isStriked)
                if (isStriked) workerModel.applyTodoChecked(todoData)
                else workerModel.cancelWork(todoData.todoName)
            }
        }
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