package com.example.todoapp.model.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDAO {

    @Query("SELECT * from todoData")
    fun getTodoList(): Flow<List<TodoData>>

    @Query("SELECT * from todoData WHERE id = :id")
    fun getTodoById(id: Int): Flow<TodoData>

        // Specify the conflict strategy as IGNORE, when the user tries to add an
        // existing Item into the database.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todoData: TodoData)

    @Update
    suspend fun update(todoData: TodoData)

    @Delete
    suspend fun delete(todoData: TodoData)
}