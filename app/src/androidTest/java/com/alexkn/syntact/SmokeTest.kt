package com.alexkn.syntact

import android.content.Context
import android.os.AsyncTask
import android.view.inputmethod.InputMethodManager
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.alexkn.syntact.app.MainActivity
import com.alexkn.syntact.presentation.bucketlist.BucketAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SmokeTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")


    @get:Rule
    var activityScenarioRule = androidx.test.ext.junit.rules.activityScenarioRule<MainActivity>()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun smokeTest() = runBlockingTest {

        launch {
            onView(withId(R.id.createBucketFab)).perform(click())
            onView(withId(R.id.header)).check(matches(withText("Create New")))
            Thread.sleep(1000)
            onView(allOf(withId(R.id.chooseButton), isDisplayed())).perform(click())
            onView(withId(R.id.startButton)).perform(click())
            val inputMethodManager = InstrumentationRegistry.getInstrumentation().targetContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            assertThat(inputMethodManager.isAcceptingText, `is`(true))

            Thread.sleep(1000)
            onView(withId(R.id.textView4)).check(matches(withText("Dog")))
            onView(withId(R.id.solutionInput)).perform(typeText("Katze"))
            onView(withId(R.id.textView4)).check(matches(withText("Dog")))
            onView(withId(R.id.solutionInput)).perform(clearText(), typeText("Hund"))
            Thread.sleep(1000)
            onView(withId(R.id.textView4)).check(matches(withText("Cat")))
        }
    }

}
