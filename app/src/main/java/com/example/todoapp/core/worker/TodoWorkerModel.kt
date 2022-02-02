package com.example.todoapp.core.worker

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.*
import com.example.todoapp.core.constants.TODO_MANIPULATION_WORK_NAME
import com.example.todoapp.core.constants.KEY_TODO_ID
import com.example.todoapp.core.constants.KEY_TODO_NAME
import com.example.todoapp.core.constants.TAG_OUTPUT
import com.example.todoapp.core.data.source.entity.TodoEntity
import java.util.concurrent.TimeUnit

@SuppressLint("EnqueueWork")
class TodoWorkerModel(application: Application) : AndroidViewModel(application) {
    private val outputWorkInfos: LiveData<List<WorkInfo>>
    private val workManager = WorkManager.getInstance(application)
    private var continuation: WorkContinuation

    init {
        outputWorkInfos = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)
        continuation = workManager
            .beginUniqueWork(
                TODO_MANIPULATION_WORK_NAME,
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
    internal fun applyTodoChecked(todoItem: TodoEntity) {
        val workerBuilder = OneTimeWorkRequestBuilder<SaveCacheTodoWorker>()
            .addTag(todoItem.todoName)

        workerBuilder.setInputData(createInputDataForUri(todoItem.id, todoItem.todoName))
        workerBuilder.setInitialDelay(5, TimeUnit.SECONDS)

//        continuation = continuation.then(workerBuilder.build())

        val deleteBuilder = OneTimeWorkRequestBuilder<RemoveTodoWorker>()
            .addTag(todoItem.todoName)

        deleteBuilder.setInputData(createInputDataForUri(todoItem.id, todoItem.todoName))
        deleteBuilder.setInitialDelay(5, TimeUnit.SECONDS)

        continuation = continuation.then(deleteBuilder.build())

        continuation.enqueue()
    }

    internal fun cancelWork(todoItem: String) {
        workManager.cancelAllWorkByTag(todoItem)
        workManager.cancelAllWorkByTag(TAG_OUTPUT)
    }

}