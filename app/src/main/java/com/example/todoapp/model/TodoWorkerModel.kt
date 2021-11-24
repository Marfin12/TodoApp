package com.example.todoapp.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.*
import com.example.todoapp.constants.IMAGE_MANIPULATION_WORK_NAME
import com.example.todoapp.constants.KEY_IMAGE_TODO
import com.example.todoapp.constants.TAG_OUTPUT
import com.example.todoapp.worker.CleanupWorker
import com.example.todoapp.worker.TodoWorker
import java.util.concurrent.TimeUnit

class TodoWorkerModel(application: Application) : AndroidViewModel(application) {
    internal val outputWorkInfos: LiveData<List<WorkInfo>>
    private val workManager = WorkManager.getInstance(application)
    private var continuation: WorkContinuation

    init {
        // This transformation makes sure that whenever the current work Id changes the WorkInfo
        // the UI is listening to changes
        outputWorkInfos = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)
        continuation = workManager
            .beginUniqueWork(
                IMAGE_MANIPULATION_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(CleanupWorker::class.java)
            )
    }

    /**
     * Creates the input data bundle which includes the Uri to operate on
     * @return Data which contains the Image Uri as a String
     */
    private fun createInputDataForUri(todoItem: String): Data {
        val builder = Data.Builder()
        builder.putString(KEY_IMAGE_TODO, todoItem)

        return builder.build()
    }

    /**
     * Create the WorkRequest to apply the blur and save the resulting image
     * @param blurLevel The amount to blur the image
     */
    internal fun applyTodoChecked(todoItem: String) {
        val str = "a"
        val workerBuilder = OneTimeWorkRequestBuilder<TodoWorker>()
            .addTag(todoItem)

        workerBuilder.setInputData(createInputDataForUri(todoItem))
        workerBuilder.setInitialDelay(5, TimeUnit.SECONDS)

        continuation = continuation.then(workerBuilder.build())

            // Actually start the work
        continuation.enqueue()
    }

    internal fun cancelWork(todoItem: String) {
        workManager.cancelAllWorkByTag(todoItem)
    }

}