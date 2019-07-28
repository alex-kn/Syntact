package com.alexkn.syntact

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.alexkn.syntact.app.MainActivity
import com.alexkn.syntact.presentation.bucketlist.BucketAdapter
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SmokeTest {

    @get:Rule
    var activityScenarioRule = androidx.test.ext.junit.rules.activityScenarioRule<MainActivity>()

    @Test
    fun smokeTest() {

        onView(withId(R.id.createBucketFab)).perform(click())
        onView(withId(R.id.textView)).check(matches(withText("Create New")))
        onView(withId(R.id.chooseButton)).perform(click())
        onView(withId(R.id.languagesList)).perform(RecyclerViewActions.actionOnItemAtPosition<BucketAdapter.BucketViewHolder>(0, click()))
        val inputMethodManager = InstrumentationRegistry.getInstrumentation().targetContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        assertThat(inputMethodManager.isAcceptingText, `is`(true))

        onView(withId(R.id.textView4)).check(matches(withText("Dog")))
        onView(withId(R.id.solutionInput)).perform(typeText("Katze"))
        onView(withId(R.id.textView4)).check(matches(withText("Dog")))
        onView(withId(R.id.solutionInput)).perform(clearText(), typeText("Hund"))
        onView(withId(R.id.textView4)).check(matches(withText("Cat")))
    }
}
