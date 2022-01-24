package com.example.todoapp.submission

import androidx.lifecycle.*
import com.example.todoapp.core.data.source.room.TodoDAO
import com.example.todoapp.core.data.source.entity.TodoEntity
import com.example.todoapp.core.model.TodoList
import com.example.todoapp.selection.SelectionAdapter
import com.example.todoapp.todo.TodoListModel
import kotlinx.coroutines.launch

/**
 * ViewModel for the submission list screen.
 */
class SubmissionModel(private val todoDAO: TodoDAO) : ViewModel() {
    private val submissionAdapter: SubmissionAdapter = SubmissionAdapter(ArrayList())

    /**
     * Store & save todo item in device database
     *
     * @param todos Can be [listOf(TodoList(id = 1, todoName = "item 1")],
     * @param action Can be [anyUnit]
     */
    fun submitItem(todos: TodoList, action: () -> Unit) {
        viewModelScope.launch {
            todos.todoList.forEach { todo ->
                todoDAO.insert(TodoEntity(todoName = todo.todoItem))
            }
        }
        action()
    }

    /**
     * Show todo item list
     *
     * @param todoList Can be [listOf(TodoList(id = 1, todoName = "item 1")],
     */
    fun addTodoItem(todoList: TodoList) {
        todoList.todoList.forEach { todo ->
            submissionAdapter.addTodoItem(todo)
        }
    }

    /**
     * Get current adapter of todo item adapter for applying its list
     *
     * @return [SubmissionAdapter] - Adapter list of submission todo item
     */
    fun getSubmissionAdapter(): SubmissionAdapter {
        return submissionAdapter
    }

    /**
     * Get all todo item list for storing into android device
     *
     * @return [TodoList] - list of submitted todo item
     */
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