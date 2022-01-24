package com.example.todoapp.core.data.source.room

import androidx.room.*
import com.example.todoapp.core.data.source.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the tasks table.
 */
@Dao
interface TodoDAO {

    /**
     * Observes list of todo item.
     *
     * @return all todo items.
     */
    @Query("SELECT * from todoentity")
    fun getTodoList(): Flow<List<TodoEntity>>

    /**
     * Observes list of todo item based on id.
     * @param id the task id.
     *
     * @return all todo item based on id.
     */
    @Query("SELECT * from todoentity WHERE id = :id")
    fun getTodoById(id: Int): Flow<TodoEntity>

    /**
     * Insert a todo item in the database.
     *
     * @param todoEntity the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todoEntity: TodoEntity)

    /**
     * Update todo item.
     *
     * @param todoEntity todo item to be updated
     * @return the number of todo item updated. This should always be 1.
     */
    @Update
    suspend fun update(todoEntity: TodoEntity)

    /**
     * Delete all completed todo items from the table.
     *
     * @return the number of todo items deleted.
     */
    @Delete
    suspend fun delete(todoEntity: TodoEntity)
}