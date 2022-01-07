package com.example.todoapp.controller.worker

import android.content.Context
import android.content.Context.MODE_APPEND
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoapp.model.constants.FILE_NAME_TODO
import com.example.todoapp.model.constants.KEY_TODO_ID
import com.example.todoapp.model.constants.KEY_TODO_NAME
import com.example.todoapp.model.data.TodoData
import com.example.todoapp.model.data.TodoDatabase
import java.io.FileOutputStream
import java.io.IOException

class RemoveTodoWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    val context = ctx

    override suspend fun doWork(): Result {

        val appContext = applicationContext
        val resourceTodoId = inputData.getInt(KEY_TODO_ID, -1)
        val resourceTodoName = inputData.getString(KEY_TODO_NAME)
        val fos: FileOutputStream = appContext.openFileOutput(FILE_NAME_TODO, MODE_APPEND)

        makeStatusNotification(resourceTodoName.toString(), appContext, 75)
        sleep()

        return try {
            resourceTodoName?.let { TodoData(id = resourceTodoId, todoName = it) }?.let {
                TodoDatabase.getDatabase(context).todoDao().delete(
                    it
                )
            }

            Result.success()
        } catch (throwable: Throwable) {
            println(throwable)
            Result.failure()
        } finally {
            try {
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}