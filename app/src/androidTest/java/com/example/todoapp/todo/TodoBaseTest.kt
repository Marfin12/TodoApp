package com.example.todoapp.todo

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.todoapp.*
import com.example.todoapp.DrawableMatcher.withDrawable
import org.hamcrest.Matchers.not

open class TodoBaseTest {
    fun testDefaultState(imageAdd: Int, title: Int, errorContent: Int) {
        Espresso.onView(withId(R.id.incFragmentEmpty)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
        Espresso.onView(withId(R.id.imgAdd)).check(
            ViewAssertions.matches(withDrawable(imageAdd))
        )
        Espresso.onView(withId(R.id.txtTitle)).check(
            ViewAssertions.matches(ViewMatchers.withText(title))
        )
        Espresso.onView(withId(R.id.txtErrorContent)).check(
            ViewAssertions.matches(ViewMatchers.withText(errorContent))
        )
    }

    fun testNavigateToAddTodoListWithImage() {
        Espresso.onView(withId(R.id.imgAdd)).perform(click())
    }
}