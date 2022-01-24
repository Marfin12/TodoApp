package com.example.todoapp.todo

import androidx.lifecycle.*
import com.example.todoapp.core.worker.TodoWorkerModel
import com.example.todoapp.core.data.source.room.TodoDAO
import com.example.todoapp.core.data.source.entity.TodoEntity

/**
 * ViewModel for the todo list screen.
 */
class TodoListModel(private val todoDAO: TodoDAO) : ViewModel() {
    val allTodoEntity: LiveData<List<TodoEntity>> = todoDAO.getTodoList().asLiveData()
    private var todoListAdapter: TodoListAdapter = TodoListAdapter(listOf())

    /**
     * Update the todo item status whether it is done or not indicated by striked style.
     *
     * @param todoEntityList Can be [listOf(TodoEntity(id = 0, todoName = "example"))],
     * @param workerModel Can be [ViewModelProvider(this).get(anyWorkerModel::class.java)]
     */
    fun updateTodoList(todoEntityList: List<TodoEntity>, workerModel: TodoWorkerModel) {
        todoListAdapter = TodoListAdapter(todoEntityList)
        todoEntityList.forEach {
            todoListAdapter.onItemClick = {todoData, isStriked ->
                if (isStriked) workerModel.applyTodoChecked(todoData)
                else workerModel.cancelWork(todoData.todoName)
            }
        }
    }

    /**
     * Get current state of todo item adapter for applying its list
     *
     * @return [TodoListAdapter] - Adapter list of todo item
     */
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