package com.alexkn.syntact

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.alexkn.syntact.app.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SmokeTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    @get:Rule
    var activityScenarioRule = androidx.test.ext.junit.rules.activityScenarioRule<MainActivity>()

    @Before
    fun setUp() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testCoroutineScope.cleanupTestCoroutines()
    }

    @Test
    fun smokeTest() = runBlockingTest {
        onView(withId(R.id.createBucketFab)).perform(click())
        onView(withId(R.id.keywordsInputLeft)).perform(click())
        val inputMethodManager = InstrumentationRegistry.getInstrumentation().targetContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        assertThat(inputMethodManager.isAcceptingText, `is`(true))
        onView(withId(R.id.keywordsInputLeft)).perform(typeText("Ich"))
        onView(withId(R.id.addTextButton)).perform(click())

        onView(allOf(withId(R.id.deckCreationItemRoot), isDisplayed())).perform(click())
        onView(withId(R.id.deckCreationDetailCancelButton)).perform(click())
        onView(withId(R.id.finishDeckFab)).perform(click())

        onView(anyOf(withId(R.id.bucketNameLabel))).check(matches(isDisplayed()))

    }
}
