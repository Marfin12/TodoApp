package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.TodoDatabase

class TodoApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database: TodoDatabase by lazy { TodoDatabase.getDatabase(this) }
}