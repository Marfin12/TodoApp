package com.example.todoapp.submission

import androidx.lifecycle.*
import com.example.todoapp.core.data.source.room.TodoDAO
import com.example.todoapp.core.data.source.entity.TodoEntity
import com.example.todoapp.core.model.TodoList
import com.example.todoapp.todo.TodoListModel
import kotlinx.coroutines.launch

class SubmissionModel(private val todoDAO: TodoDAO) : ViewModel() {
    private val submissionAdapter: SubmissionAdapter = SubmissionAdapter(ArrayList())

    fun submitItem(todos: TodoList, action: () -> Unit) {
        viewModelScope.launch {
            todos.todoList.forEach { todo ->
                todoDAO.insert(TodoEntity(todoName = todo.todoItem))
            }
        }
        action()
    }

    fun addTodoItem(todoList: TodoList) {
        todoList.todoList.forEach { todo ->
            submissionAdapter.addTodoItem(todo)
        }
    }

    fun getSubmissionAdapter(): SubmissionAdapter {
        return submissionAdapter
    }

    fun getSubmissionTodoList(): TodoList {
        return submissionAdapter.getTodoList()
    }

    class SubmissionViewModelFactory(private val todoDAO: TodoDAO) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SubmissionModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TodoListModel(todoDAO) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}