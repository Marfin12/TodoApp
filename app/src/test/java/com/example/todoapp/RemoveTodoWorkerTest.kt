package com.example.todoapp

import android.content.Context
import android.content.Context.MODE_APPEND
import android.os.Looper
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import androidx.work.impl.utils.futures.SettableFuture
import androidx.work.impl.utils.taskexecutor.TaskExecutor
import com.example.todoapp.UnitTestSetup.handlePendingIntent
import com.example.todoapp.core.constants.FILE_NAME_TODO
import com.example.todoapp.core.constants.KEY_TODO_NAME
import com.example.todoapp.core.data.source.room.TodoDatabase
import com.example.todoapp.core.worker.RemoveTodoWorker
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import java.io.FileOutputStream

class RemoveTodoWorkerTest {

    private val FAKE_STRING = "MESSAGE"
    private val FAKE_TODO_NAME = "TODO_NAME"

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockWorkerParameters: WorkerParameters

    @Mock
    private lateinit var mockExecutor: TaskExecutor

    @Mock
    private lateinit var mockListenableWorker: SettableFuture<ListenableWorker.Result>

    @Mock
    private lateinit var mockInputData: Data

    @Mock
    private lateinit var mockFileOutputStream: FileOutputStream

    @Mock
    private lateinit var mockTodoDatabase: TodoDatabase

    @Mock
    private lateinit var mockLooper: Looper

    @Before
    fun setup() {
        mockkStatic(SettableFuture::class)
        mockkStatic(Log::class)
        mockkStatic(Looper::class)
        every { Log.e(any(), any()) } returns 0
        mockContext = mock(Context::class.java)
        mockLooper = mock(Looper::class.java)
        mockTodoDatabase = mock(TodoDatabase::class.java)
        mockWorkerParameters = mock(WorkerParameters::class.java)
        mockExecutor = mock(TaskExecutor::class.java)
        mockInputData = mock(Data::class.java)
        mockFileOutputStream = mock(FileOutputStream::class.java)
        mockListenableWorker = mock(SettableFuture::class.java) as SettableFuture<ListenableWorker.Result>
        every { SettableFuture.create<ListenableWorker.Result>() } returns mockListenableWorker
        handlePendingIntent(mockContext)
    }

    @Test
    fun test_remove_todo_worker_success() {
        `when`(mockWorkerParameters.taskExecutor).thenReturn(mockExecutor)
        `when`(mockWorkerParameters.inputData).thenReturn(mockInputData)
        `when`(mockContext.openFileOutput(FILE_NAME_TODO, MODE_APPEND)).thenReturn(mockFileOutputStream)
        `when`(mockContext.getString(R.string.remove_todo_worker)).thenReturn(FAKE_STRING)
        val worker = RemoveTodoWorker(mockContext, mockWorkerParameters)
        runBlocking {
            val res = worker.doWork()
            assertThat(res, `is`(ListenableWorker.Result.success()))
            verify(mockFileOutputStream, times(1)).close()
        }
    }

    @Test
    fun test_remove_todo_worker_failure() {
        `when`(mockWorkerParameters.taskExecutor).thenReturn(mockExecutor)
        `when`(mockWorkerParameters.inputData).thenReturn(mockInputData)
        `when`(mockInputData.getString(KEY_TODO_NAME)).thenReturn(FAKE_TODO_NAME)
        `when`(mockContext.openFileOutput(FILE_NAME_TODO, MODE_APPEND)).thenReturn(mockFileOutputStream)
        `when`(mockContext.getString(R.string.remove_todo_worker)).thenReturn(FAKE_STRING)
        `when`(mockContext.applicationContext).thenReturn(mockContext)
        val worker = RemoveTodoWorker(mockContext, mockWorkerParameters)
        runBlocking {
            val res = worker.doWork()
            assertThat(res, `is`(ListenableWorker.Result.failure()))
            verify(mockFileOutputStream, times(1)).close()
        }
    }
}