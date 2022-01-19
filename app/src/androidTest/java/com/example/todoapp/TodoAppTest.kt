package com.example.todoapp

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.todoapp.view.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumentation tests to be run on a physical device or emulator.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class TodoAppTest : BaseTest() {

    @Before
    fun setup() {
        launchActivity<MainActivity>()
    }

    /**
     * Test the view components of the pick lemon state
     */
    @Test
    fun `test_initial_state`() {
        testState(R.string.app_name, R.drawable.ic_baseline_border_clear_24)
    }
}