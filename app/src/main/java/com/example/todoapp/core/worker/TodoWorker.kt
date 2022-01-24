package com.example.todoapp.core.worker

import android.content.Context
import android.content.Context.MODE_APPEND
import android.text.TextUtils
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.todoapp.R
import com.example.todoapp.core.constants.FILE_NAME_TODO
import com.example.todoapp.core.constants.KEY_TODO_NAME
import com.example.todoapp.core.constants.TAG_TODO_WORKER
import java.io.FileOutputStream
import java.io.IOException

class TodoWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        val appContext = applicationContext
        val resourceUri = inputData.getString(KEY_TODO_NAME)
        val fos: FileOutputStream = appContext.openFileOutput(FILE_NAME_TODO, MODE_APPEND)

        makeStatusNotification(
            "You tick ${resourceUri.toString()} item as indicating is done",
            appContext.getString(R.string.todo_worker_progress),
            appContext
        )
        sleep()

        return try {
            if (TextUtils.isEmpty(resourceUri)) {
                Log.e(TAG_TODO_WORKER,"Input uri is empty")
                throw IllegalArgumentException("Invalid input uri")
            }
            if (resourceUri != null) {
                Log.d(TAG_TODO_WORKER, resourceUri)
                fos.write(resourceUri.toByteArray())
            }

            Result.success()
        } catch (throwable: Throwable) {
            Log.e(TAG_TODO_WORKER, throwable.message!!)
            Result.failure()
        } finally {
            try {
                fos.close()
            } catch (e: IOException) {
                Log.e(TAG_TODO_WORKER, e.message!!)
                e.printStackTrace()
            }
        }
    }
}