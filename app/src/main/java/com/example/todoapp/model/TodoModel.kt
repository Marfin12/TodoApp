package com.example.todoapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.adapter.SubmissionAdapter
import com.example.todoapp.adapter.TodoAdapter

class TodoModel() : ViewModel() {
    private val _index = MutableLiveData(0)
    private val index: LiveData<Int>
        get() = _index

    private val todoAdapter: TodoAdapter = TodoAdapter(ArrayList())
    private val submissionAdapter: SubmissionAdapter = SubmissionAdapter(ArrayList())

    fun addTodoItem(todoItem: String, drawableId: Int) {
        index.value?.let { Todo(it,todoItem, drawableId) }?.let { todo ->
            todoAdapter.addTodoItem(todo)
        }
        _index.value = _index.value?.plus(1)
    }


    fun addTodoItem(todoList: TodoList) {
        todoList.todoList.forEach { todo ->
            submissionAdapter.addTodoItem(todo)
        }
    }

    fun getTodoAdapter(): TodoAdapter {
        return todoAdapter
    }

    fun getSubmissionAdapter(): SubmissionAdapter {
        return submissionAdapter
    }

    fun getTodoList(): TodoList {
        return todoAdapter.getTodoList()
    }

//    private fun getNewTodoEntry(todo: Todo): TodoData {
//        return TodoData(
//            todoName = todo.todoItem
//        )
//    }

//    private fun submitTodoItem(todo: Todo) {
//        viewModelScope.launch {
//            todoDao.insert(getNewTodoEntry(todo))
//        }
//    }
    
}