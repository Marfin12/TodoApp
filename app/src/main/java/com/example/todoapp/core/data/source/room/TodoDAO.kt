package com.example.todoapp.core.data.source.room

import androidx.room.*
import com.example.todoapp.core.data.source.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDAO {
    @Query("SELECT * from todoentity")
    fun getTodoList(): Flow<List<TodoEntity>>

    @Query("SELECT * from todoentity WHERE id = :id")
    fun getTodoById(id: Int): Flow<TodoEntity>

        // Specify the conflict strategy as IGNORE, when the user tries to add an
        // existing Item into the database.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todoEntity: TodoEntity)

    @Update
    suspend fun update(todoEntity: TodoEntity)

    @Delete
    suspend fun delete(todoEntity: TodoEntity)
}