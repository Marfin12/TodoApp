package com.example.todoapp.todo

import androidx.lifecycle.*
import com.example.todoapp.core.worker.TodoWorkerModel
import com.example.todoapp.core.data.source.room.TodoDAO
import com.example.todoapp.core.data.source.entity.TodoEntity

class TodoListModel(private val todoDAO: TodoDAO) : ViewModel() {
    val allTodoEntity: LiveData<List<TodoEntity>> = todoDAO.getTodoList().asLiveData()
    private var todoListAdapter: TodoListAdapter = TodoListAdapter(listOf())

    fun updateTodoList(todoEntityList: List<TodoEntity>, workerModel: TodoWorkerModel) {
        todoListAdapter = TodoListAdapter(todoEntityList)
        todoEntityList.forEach {
            todoListAdapter.onItemClick = {todoData, isStriked ->
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