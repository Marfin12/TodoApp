package com.example.todoapp.selection

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.todoapp.R
import com.example.todoapp.isRecycleViewEmpty
import org.hamcrest.Matchers

open class SelectionBaseTest {
    fun testDefaultState(addBtn: Int, confirmBtn: Int) {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).check(
            ViewAssertions.matches(isRecycleViewEmpty())
        )
        Espresso.onView(ViewMatchers.withId(R.id.todoEditText)).check(
            ViewAssertions.matches(Matchers.not(ViewMatchers.isFocused()))
        )
        Espresso.onView(ViewMatchers.withId(R.id.addBtn)).check(
            ViewAssertions.matches(ViewMatchers.withText(addBtn))
        )
        Espresso.onView(ViewMatchers.withId(R.id.submitBtn)).check(
            ViewAssertions.matches(ViewMatchers.withText(confirmBtn))
        )
    }

    fun testListIsNotEmpty() {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).check(
            ViewAssertions.matches(Matchers.not(isRecycleViewEmpty()))
        )
    }

    fun testListIsEmpty() {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).check(
            ViewAssertions.matches(isRecycleViewEmpty())
        )
    }

    fun testRemoveTodoItem() {
        Espresso.onView(ViewMatchers.withId(R.id.imgAdd)).perform(click())
    }

    fun testFillInTheTodoItemField(itemName: String) {
        Espresso.onView(ViewMatchers.withId(R.id.todoEditText)).perform(click()).perform(
            typeText(itemName)
        ).perform(closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.addBtn))
            .perform(click())
    }

    fun testNavigateToSubmitTheTodoItemList() {
        Espresso.onView(ViewMatchers.withId(R.id.submitBtn)).perform(click())
    }
}