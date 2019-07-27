package com.alexkn.syntact

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexkn.syntact.app.MainActivity
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import com.alexkn.syntact.data.common.AppDatabase

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    var activityScenarioRule = androidx.test.ext.junit.rules.activityScenarioRule<MainActivity>()


    @Test
    fun test() {

        onView(withId(R.id.createBucketFab)).perform(click())

        onView(withId(R.id.textView)).check(matches(withText("Create New")))
    }
}
