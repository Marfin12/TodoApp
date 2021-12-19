package com.example.todoapp.model.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Todo (
    val id: Int,
    val todoItem: String,
    @DrawableRes val imageResourceId: Int
): Parcelable
