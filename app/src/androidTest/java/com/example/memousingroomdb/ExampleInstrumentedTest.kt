package com.example.memousingroomdb

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule


import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun 메모_추가하기_버튼을_눌렀을_때_추가하기_화면으로_넘어가는지_확인() {
        onView(withId(R.id.btn_add))
            .perform(click())

        onView(withId(R.id.tv_title))
            .check(matches(isDisplayed()))
    }
}