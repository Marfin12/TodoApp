package com.example.todoapp.todo

import androidx.fragment.app.FragmentTransaction
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.todoapp.R
import com.example.todoapp.MainActivity
import com.example.todoapp.core.data.source.entity.TodoEntity
import com.example.todoapp.core.data.source.room.TodoDAO
import com.example.todoapp.core.model.Todo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumentation tests to be run on a physical device or emulator.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class TodoTests : TodoBaseTest() {
    @get:Rule
    var activityRule: ActivityTestRule<*> = ActivityTestRule(
        MainActivity::class.java
    )

    @Before
    fun setup() {
        InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .deleteDatabase("todoentity")
    }

    /**
     * Test the view components of the pick lemon state
     */
    @Test
    fun test_initial_state() {
        testDefaultState(
            R.drawable.ic_baseline_border_clear_24,
            R.string.no_todo_list,
            R.string.click_image_todo_list
        )
    }

    /**
     * Test the view components of the pick lemon state
     */
    @Test
    fun test_navigate_to_selection_screen() {
        testNavigateToAddTodoListWithImage()
    }
}