package com.alexkn.syntact

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.alexkn.syntact.app.MainActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
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
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        testCoroutineScope.cleanupTestCoroutines()
    }

    @Test
    fun smokeTest() = runBlockingTest {
        launch {
            onView(withId(R.id.createBucketFab)).perform(click())
            onView(withId(R.id.header)).check(matches(withText("Choose")))
            onView(allOf(withId(R.id.chooseButton), isDisplayed())).perform(click())
            onView(withId(R.id.startButton)).perform(click())
            val inputMethodManager = InstrumentationRegistry.getInstrumentation().targetContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            assertThat(inputMethodManager.isAcceptingText, `is`(true))
            onView(withId(R.id.textView4)).check(matches(withText("Hello")))
            onView(withId(R.id.solutionInput)).perform(typeText("Katze"))
            onView(withId(R.id.textView4)).check(matches(withText("Hello")))
            onView(withId(R.id.solutionInput)).perform(clearText(), typeText("Hallo"))
            onView(withId(R.id.textView4)).check(matches(withText("Beer")))
        }
    }
}
