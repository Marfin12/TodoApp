package com.example.todoapp.controller.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.*
import com.example.todoapp.controller.worker.CleanupWorker
import com.example.todoapp.controller.worker.RemoveTodoWorker
import com.example.todoapp.controller.worker.TodoWorker
import com.example.todoapp.model.constants.IMAGE_MANIPULATION_WORK_NAME
import com.example.todoapp.model.constants.KEY_TODO_ID
import com.example.todoapp.model.constants.KEY_TODO_NAME
import com.example.todoapp.model.constants.TAG_OUTPUT
import com.example.todoapp.model.data.TodoData
import java.util.concurrent.TimeUnit

class TodoWorkerModel(application: Application) : AndroidViewModel(application) {
    private val outputWorkInfos: LiveData<List<WorkInfo>>
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
    private fun createInputDataForUri(todoId: Int, todoItem: String): Data {
        val builder = Data.Builder()
        builder.putString(KEY_TODO_NAME, todoItem)
        builder.putInt(KEY_TODO_ID, todoId)

        return builder.build()
    }

    /**
     * Create the WorkRequest to apply the blur and save the resulting image
     * @param blurLevel The amount to blur the image
     */
    internal fun applyTodoChecked(todoItem: TodoData) {
        val workerBuilder = OneTimeWorkRequestBuilder<TodoWorker>()
            .addTag(todoItem.todoName)

        workerBuilder.setInputData(createInputDataForUri(todoItem.id, todoItem.todoName))
        workerBuilder.setInitialDelay(5, TimeUnit.SECONDS)

        continuation = continuation.then(workerBuilder.build())

        val deleteBuilder = OneTimeWorkRequestBuilder<RemoveTodoWorker>()
            .addTag(todoItem.todoName)

        deleteBuilder.setInputData(createInputDataForUri(todoItem.id, todoItem.todoName))
        deleteBuilder.setInitialDelay(5, TimeUnit.SECONDS)

        continuation = continuation.then(deleteBuilder.build())
            // Actually start the work
        continuation.enqueue()
    }

    internal fun cancelWork(todoItem: String) {
        workManager.cancelAllWorkByTag(todoItem)
        workManager.cancelAllWorkByTag(TAG_OUTPUT)
    }

}