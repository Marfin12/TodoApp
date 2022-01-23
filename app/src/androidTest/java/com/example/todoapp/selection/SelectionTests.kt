package com.example.todoapp.selection

import androidx.fragment.app.FragmentTransaction
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.todoapp.MainActivity
import com.example.todoapp.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.todoapp.buildLaunchFragmentIntent

/**
 * Instrumentation tests to be run on a physical device or emulator.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class SelectionTests : SelectionBaseTest() {
    @get:Rule
    var activityRule: ActivityTestRule<*> = ActivityTestRule(
        MainActivity::class.java
    )

    @Before
    fun setup() {
        val launchFragmentIntent = buildLaunchFragmentIntent(R.id.selectionFragment)
        activityRule.launchActivity(launchFragmentIntent)
    }

    /**
     * Test the view components of the pick lemon state
     */
    @Test
    fun test_initial_state() {
        testDefaultState(
            R.string.add,
            R.string.confirm,
        )
    }

    /**
     * Test the view components of the pick lemon state
     */
    @Test
    fun test_remove_item() {
        testFillInTheTodoItemField("item 1")
        testListIsNotEmpty()
        testRemoveTodoItem()
        testListIsEmpty()
    }

    /**
     * Test the view components of the pick lemon state
     */
    @Test
    fun test_add_item() {
        testFillInTheTodoItemField("item 2")
        testListIsNotEmpty()
        testNavigateToSubmitTheTodoItemList()
    }
}