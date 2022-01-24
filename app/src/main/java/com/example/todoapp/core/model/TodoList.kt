package com.example.todoapp.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * model class for list of todo item. In order to compile with Room, we can't use @JvmOverloads to
 * generate multiple constructors.
 *
 * @param todoList  list of todo item
 */
@Parcelize
data class TodoList (
    val todoList: List<Todo>
): Parcelable
