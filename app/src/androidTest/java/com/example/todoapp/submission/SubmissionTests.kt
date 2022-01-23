package com.example.todoapp.submission

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.todoapp.MainActivity
import com.example.todoapp.R
import com.example.todoapp.buildLaunchFragmentIntent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumentation tests to be run on a physical device or emulator.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class SubmissionTests : SubmissionBaseTest() {
    @get:Rule
    var activityRule: ActivityTestRule<*> = ActivityTestRule(
        MainActivity::class.java
    )

    @Before
    fun setup() {
        val launchFragmentIntent = buildLaunchFragmentIntent(R.id.submissionFragment)
        activityRule.launchActivity(launchFragmentIntent)
    }

    /**
     * Test the view components of the pick lemon state
     */
    @Test
    fun test_initial_state() {
        testDefaultState(
            R.string.discard,
            R.string.submit
        )
    }

    /**
     * Test the view components of the pick lemon state
     */
    @Test
    fun test_discard_navigate_to_todo_page() {
        testDiscardButtonToNavigateToTodoFragment()
        testTodoListIsEmptyInTodoFragment()
    }

    /**
     * Test the view components of the pick lemon state
     */
    @Test
    fun test_submit_navigate_to_todo_page() {
        testSubmitButtonToNavigateToTodoFragment()
        testTodoListIsNotEmptyInTodoFragment()
    }
}