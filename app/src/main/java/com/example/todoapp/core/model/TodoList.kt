package com.example.todoapp.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TodoList (
    val todoList: List<Todo>
): Parcelable
