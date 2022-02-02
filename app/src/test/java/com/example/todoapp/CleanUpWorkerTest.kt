package com.example.todoapp

import android.content.Context
import android.util.Log
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Test
import org.mockito.Mock
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.work.*
import com.example.todoapp.UnitTestSetup.handlePendingIntent
import com.example.todoapp.core.worker.CleanupWorker
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mockito.*
import java.io.File

class CleanUpWorkerTest {

    private val FAKE_MESSAGE = "MESSAGE"
    private val FAKE_GET_STRING = "GET_STRING"

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockWorkerParameters: WorkerParameters

    @Mock
    private lateinit var mockFiles: File

    @Before
    fun setupCleanUpWorker() {
        mockkStatic(Log::class)
        mockContext = mock(Context::class.java)
        mockWorkerParameters = mock(WorkerParameters::class.java)
        mockFiles = mock(File::class.java)
        `when`(mockContext.getString(anyInt())).thenReturn(FAKE_GET_STRING)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        handlePendingIntent(mockContext)
    }

    @Test
    fun test_clean_up_worker_has_list_of_files() {
        val cleanUp = CleanupWorker(mockContext, mockWorkerParameters)
        `when`(mockContext.filesDir).thenReturn(mockFiles)

        val mockListFiles = listOf(mockFiles).toTypedArray()
        `when`(mockFiles.listFiles()).thenReturn(mockListFiles)

        mockListFiles.forEach {
            `when`(it.name).thenReturn(FAKE_MESSAGE)
        }

        val res = cleanUp.doWork()
        mockListFiles.forEach {
            verify(it, times(1)).name
            verify(it, times(1)).delete()
        }
        assert(res.toString() == "Success {mOutputData=Data {}}")
    }

    @Test
    fun test_clean_up_worker_has_empty_file_name() {
        val cleanUp = CleanupWorker(mockContext, mockWorkerParameters)
        `when`(mockContext.filesDir).thenReturn(mockFiles)

        val mockListFiles = listOf(mockFiles).toTypedArray()
        `when`(mockFiles.listFiles()).thenReturn(mockListFiles)

        mockListFiles.forEach {
            `when`(it.name).thenReturn("")
        }

        val res = cleanUp.doWork()
        mockListFiles.forEach {
            verify(it, times(1)).name
            verify(it, never()).delete()
        }
        assert(res.toString() == "Success {mOutputData=Data {}}")
    }

    @Test
    fun test_clean_up_worker_has_empty_list_of_files() {
        val cleanUp = CleanupWorker(mockContext, mockWorkerParameters)
        `when`(mockContext.filesDir).thenReturn(mockFiles)

        val res = cleanUp.doWork()
        assert(res.toString() == "Success {mOutputData=Data {}}")
    }

    @Test
    fun test_clean_up_worker_failure() {
        val cleanUp = CleanupWorker(mockContext, mockWorkerParameters)
        val res = cleanUp.doWork()
        assert(res.toString() == "Failure {mOutputData=Data {}}")
    }
}