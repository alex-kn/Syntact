package dev.alexknittel.syntact.app

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.alexknittel.syntact.R
import dev.alexknittel.syntact.presentation.MainActivity
import dev.alexknittel.syntact.presentation.decklist.dialog.DeckListLanguageDialogItemAdaper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.hamcrest.CoreMatchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AppTest {

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
    fun smoke() = runBlockingTest {
        onView(withId(R.id.createBucketFab)).perform(click())

        onView(withId(R.id.deckListLanguageList))
                .perform(actionOnItemAtPosition<DeckListLanguageDialogItemAdaper.DeckListLanguageDialogItemViewHolder>(0, click()))

        onView(withId(R.id.keywordsInputLeft)).perform(click())
        val inputMethodManager = InstrumentationRegistry.getInstrumentation().targetContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        assertThat(inputMethodManager.isAcceptingText, `is`(true))
        onView(withId(R.id.keywordsInputLeft)).perform(typeText("Haus"))
        onView(withId(R.id.addTextButton)).perform(click())

        onView(allOf(withId(R.id.deckCreationItemRoot), isDisplayed())).perform(click())
        onView(withId(R.id.deckCreationDetailCancelButton)).perform(click())
        onView(withId(R.id.finishDeckFab)).perform(click())
        onView(withId(R.id.deckCreationCreateDeckButton)).perform(click())

        onView(anyOf(withId(R.id.bucketNameLabel))).check(matches(isDisplayed()))
        onView(anyOf(withId(R.id.startButton))).check(matches(isDisplayed()))
        onView(anyOf(withId(R.id.startButton))).perform(click())
        onView(anyOf(withId(R.id.backButton))).perform(click())
        onView(anyOf(withId(R.id.optionsButton))).check(matches(isDisplayed()))
        onView(anyOf(withId(R.id.optionsButton))).perform(click())

        onView(allOf(withId(R.id.deckDetailsItemRoot), isDisplayed())).perform(click())
        onView(withId(R.id.deckDetailsDetailCancelButton)).perform(click())
        onView(anyOf(withId(R.id.backButton))).perform(click())
    }
}
