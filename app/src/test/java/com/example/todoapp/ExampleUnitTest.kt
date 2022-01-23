package com.example.todoapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.todoapp.controller.worker.HandleNullableWorkerUtilsException
import com.example.todoapp.controller.worker.learningUnitTest
import com.example.todoapp.controller.worker.sleep
import com.example.todoapp.model.constants.CHANNEL_ID
import com.example.todoapp.model.constants.NOTIFICATION_ID
import io.mockk.every
import io.mockk.mockkStatic
import junit.framework.Assert.assertEquals
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito.*
import java.lang.Exception
import java.lang.reflect.Field
import java.lang.reflect.Modifier

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
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
    private lateinit var mockUnit: Unit

    @Mock
    private lateinit var mockNotificationManager: NotificationManager

    @Test
    fun test_function() {
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
        every { Log.d(any(), any()) } returns 0
        every { NotificationManagerCompat.from(mockContext) } returns mockNotificationManagerCompat
        every { PendingIntent.getActivity(mockContext, any(), mockIntent, any()) } returns mockPendingIntent
        setFinalStatic(VERSION::class.java.getField("SDK_INT"), 29)
        `when`(mockContext.getSystemService(Context.NOTIFICATION_SERVICE)).thenReturn(mockNotificationManager)
        learningUnitTest(mockContext, FAKE_STRING)
        verify(mockNotificationManagerCompat, times(1))
            .notify(NOTIFICATION_ID, mockNotification)
        verify(mockNotificationManager, times(1))
            .createNotificationChannel(mockNotificationChannel)
    }

    @Test
    fun test_sleep() {
        mockkStatic(Thread::class)
        every { Thread.sleep(0, 0)} returns mockUnit
        sleep()
        assertEquals(mockUnit.toString(), "aa")
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