package com.example.todoapp

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.todoapp.core.worker.HandleNullableWorkerUtilsException
import io.mockk.every
import io.mockk.mockkStatic
import org.mockito.Mock
import org.mockito.Mockito

object UnitTestSetup {

    @Mock
    private lateinit var mockIntent: Intent

    @Mock
    private lateinit var mockPendingIntent: PendingIntent

    internal fun handlePendingIntent(mockContext: Context) {
        mockkStatic(PendingIntent::class)
        mockPendingIntent = Mockito.mock(PendingIntent::class.java)
        mockIntent = Mockito.mock(Intent::class.java)
        HandleNullableWorkerUtilsException.intent = mockIntent
        every { PendingIntent.getActivity(mockContext, any(), mockIntent, any()) } returns mockPendingIntent
    }
}