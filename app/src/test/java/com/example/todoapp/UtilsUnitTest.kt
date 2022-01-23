package com.example.todoapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.example.todoapp.core.constants.NOTIFICATION_ID
import com.example.todoapp.core.worker.HandleNullableWorkerUtilsException
import com.example.todoapp.core.worker.makeStatusNotification
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito.*
import java.lang.Exception
import java.lang.reflect.Field
import java.lang.reflect.Modifier

class UtilsUnitTest {
    private val FAKE_STRING = "FAKE"

    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockIntent: Intent

    @Mock
    private lateinit var mockPendingIntent: PendingIntent

    @Mock
    private lateinit var mockNotification: Notification

    @Mock
    private lateinit var mockNotificationManagerCompat: NotificationManagerCompat

    @Mock
    private lateinit var mockNotificationChannel: NotificationChannel

    @Mock
    private lateinit var mockNotificationManager: NotificationManager

    @Before
    fun setupNotification() {
        mockkStatic(Log::class)
        mockkStatic(Intent::class)
        mockkStatic(PendingIntent::class)
        mockkStatic(NotificationManagerCompat::class)
        mockkStatic(NotificationChannel::class)
        mockContext = mock(Context::class.java)
        mockIntent = mock(Intent::class.java)
        mockPendingIntent = mock(PendingIntent::class.java)
        mockNotificationManagerCompat = mock(NotificationManagerCompat::class.java)
        mockNotification = mock(Notification::class.java)
        mockNotificationChannel = mock(NotificationChannel::class.java)
        mockNotificationManager = mock(NotificationManager::class.java)
        HandleNullableWorkerUtilsException.intent = mockIntent
        HandleNullableWorkerUtilsException.notification = mockNotification
        HandleNullableWorkerUtilsException.notificationChannel = mockNotificationChannel
    }

    @Test
    fun test_notification() {
        every { NotificationManagerCompat.from(mockContext) } returns mockNotificationManagerCompat
        every { PendingIntent.getActivity(mockContext, any(), mockIntent, any()) } returns mockPendingIntent
        setFinalStatic(VERSION::class.java.getField("SDK_INT"), 29)
        `when`(mockContext.getSystemService(Context.NOTIFICATION_SERVICE)).thenReturn(mockNotificationManager)
        makeStatusNotification(FAKE_STRING, mockContext)
        verify(mockNotificationManagerCompat, times(1))
            .notify(NOTIFICATION_ID, mockNotification)
        verify(mockNotificationManager, times(1))
            .createNotificationChannel(mockNotificationChannel)
    }

    @Throws(Exception::class)
    fun setFinalStatic(field: Field, newValue: Any?) {
        field.isAccessible = true
        val modifiersField: Field = Field::class.java.getDeclaredField("modifiers")
        modifiersField.isAccessible = true
        modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())
        field.set(null, newValue)
    }
}