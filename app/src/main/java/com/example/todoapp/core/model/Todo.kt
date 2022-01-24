package com.example.todoapp.core.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

/**
 * data class for todo item. In order to compile with Room, we can't use @JvmOverloads to
 * generate multiple constructors.
 *
 * @param id                id of the item
 * @param todoName          description of the item name
 * @param imageResourceId   additional icon for certain action
 */
@Parcelize
data class Todo (
    val id: Int,
    val todoItem: String,
    @DrawableRes val imageResourceId: Int
): Parcelable
