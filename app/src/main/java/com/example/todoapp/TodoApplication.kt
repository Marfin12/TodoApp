package com.example.todoapp

import android.app.Application
import com.example.todoapp.core.data.source.room.TodoDatabase

/**
 * An application that lazily provides a database. Note that this Todo Database is
 * used to store todo item which created by the user.
 */
class TodoApplication : Application() {
    val database: TodoDatabase by lazy { TodoDatabase.getDatabase(this) }
}