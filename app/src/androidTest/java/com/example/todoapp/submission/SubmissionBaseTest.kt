package com.example.todoapp.submission

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.todoapp.R
import com.example.todoapp.isRecycleViewEmpty
import org.hamcrest.Matchers

open class SubmissionBaseTest {
    fun testDefaultState(discardBtnText: Int, confirmBtnText: Int) {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).check(
            ViewAssertions.matches(Matchers.not(isRecycleViewEmpty()))
        )
        Espresso.onView(ViewMatchers.withId(R.id.discardBtn)).check(
            ViewAssertions.matches(ViewMatchers.withText(discardBtnText))
        )
        Espresso.onView(ViewMatchers.withId(R.id.submitBtn)).check(
            ViewAssertions.matches(ViewMatchers.withText(confirmBtnText))
        )
    }

    fun testTodoListIsNotEmptyInTodoFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).check(
            ViewAssertions.matches(Matchers.not(isRecycleViewEmpty()))
        )
    }

    fun testTodoListIsEmptyInTodoFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).check(
            ViewAssertions.matches((isRecycleViewEmpty()))
        )
    }

    fun testDiscardButtonToNavigateToTodoFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.discardBtn)).perform(click())
    }

    fun testSubmitButtonToNavigateToTodoFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.submitBtn)).perform(click())
    }
}