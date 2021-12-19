package com.example.todoapp.worker

import android.content.Context
import android.content.Context.MODE_APPEND
import android.text.TextUtils
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.todoapp.constants.FILE_NAME_TODO
import com.example.todoapp.constants.KEY_TODO_NAME
import java.io.FileOutputStream
import java.io.IOException

class TodoWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        val appContext = applicationContext
        val resourceUri = inputData.getString(KEY_TODO_NAME)
        val fos: FileOutputStream = appContext.openFileOutput(FILE_NAME_TODO, MODE_APPEND)

        makeStatusNotification(resourceUri.toString(), appContext, 75)
        sleep()

        return try {
            if (TextUtils.isEmpty(resourceUri)) {
                println("Invalid input uri")
                throw IllegalArgumentException("Invalid input uri")
            }
            if (resourceUri != null) {
                fos.write(resourceUri.toByteArray())
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