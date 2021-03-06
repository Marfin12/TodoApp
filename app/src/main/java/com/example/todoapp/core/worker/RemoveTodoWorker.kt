package com.example.todoapp.core.worker

import android.content.Context
import android.content.Context.MODE_APPEND
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoapp.R
import com.example.todoapp.core.constants.FILE_NAME_TODO
import com.example.todoapp.core.constants.KEY_TODO_ID
import com.example.todoapp.core.constants.KEY_TODO_NAME
import com.example.todoapp.core.constants.TAG_REMOVE_TODO_WORKER
import com.example.todoapp.core.data.source.entity.TodoEntity
import com.example.todoapp.core.data.source.room.TodoDatabase
import java.io.FileOutputStream
import java.io.IOException

class RemoveTodoWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {

        val resourceTodoId = inputData.getInt(KEY_TODO_ID, -1)
        val resourceTodoName = inputData.getString(KEY_TODO_NAME)
        val fos: FileOutputStream = applicationContext.openFileOutput(FILE_NAME_TODO, MODE_APPEND)

        makeStatusNotification(
            applicationContext.getString(R.string.remove_todo_worker),
            resourceTodoName.toString(),
            applicationContext
        )
        sleep()

        return try {
            resourceTodoName?.let { TodoEntity(id = resourceTodoId, todoName = it) }?.let {
                TodoDatabase.getDatabase(applicationContext).todoDao().delete(
                    it
                )
            }

            Result.success()
        } catch (throwable: Throwable) {
            Log.e(TAG_REMOVE_TODO_WORKER, throwable.message!!)
            Result.failure()
        } finally {
            try {
                fos.close()
            } catch (e: IOException) {
                Log.e(TAG_REMOVE_TODO_WORKER, e.message!!)
                e.printStackTrace()
            }
        }
    }
}