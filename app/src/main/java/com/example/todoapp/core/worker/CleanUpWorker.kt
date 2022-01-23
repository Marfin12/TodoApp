package com.example.todoapp.core.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.todoapp.core.constants.TAG_CLEAN_UP_WORKER
import com.example.todoapp.core.constants.TAG_TODO_WORKER

class CleanupWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        makeStatusNotification("Cleaning up old temporary files", applicationContext)
        sleep()

        return try {
            val entries = applicationContext.filesDir.listFiles()
            if (entries != null) {
                for (entry in entries) {
                    val name = entry.name
                    if (name.isNotEmpty()) {
                        val deleted = entry.delete()
                        Log.d(TAG_CLEAN_UP_WORKER,"Deleted $name - $deleted")
                    }
                }
            }
            Result.success()
        } catch (exception: Exception) {
            Log.e(TAG_TODO_WORKER, exception.message!!)
            Result.failure()
        }
    }
}