package com.example.todoapp.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class CleanupWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        makeStatusNotification("Cleaning up old temporary files", applicationContext, 25)
        sleep()

        return try {
            val entries = applicationContext.filesDir.listFiles()
            if (entries != null) {
                for (entry in entries) {
                    val name = entry.name
                    if (name.isNotEmpty()) {
                        val deleted = entry.delete()
                        println("Deleted $name - $deleted")
                    }
                }
            }
            Result.success()
        } catch (exception: Exception) {
            println(exception)
            Result.failure()
        }
    }
}