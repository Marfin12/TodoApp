package com.example.todoapp.model

import androidx.lifecycle.*
import com.example.todoapp.adapter.TodoListAdapter
import com.example.todoapp.data.TodoDAO
import com.example.todoapp.data.TodoData
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

    fun updateTodoList(todoDataList: List<TodoData>, workerModel: TodoWorkerModel) {
        todoListAdapter = TodoListAdapter(todoDataList)
        todoDataList.forEach {
            todoListAdapter.onItemClick = {todoData, isStriked ->
                println("clicked")
                println(isStriked)
                if (isStriked) workerModel.applyTodoChecked(todoData)
                else workerModel.cancelWork(todoData)
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