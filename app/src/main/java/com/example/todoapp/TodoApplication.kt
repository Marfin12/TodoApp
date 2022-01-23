package com.example.todoapp

import android.app.Application
import com.example.todoapp.core.data.source.room.TodoDatabase

class TodoApplication : Application() {
    val database: TodoDatabase by lazy { TodoDatabase.getDatabase(this) }
}