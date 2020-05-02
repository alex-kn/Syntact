package com.alexkn.syntact.presentation.deckcreation

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexkn.syntact.app.general.config.DaggerTestApplicationComponent
import com.alexkn.syntact.service.PhraseSuggestionResponse
import com.alexkn.syntact.service.Suggestion
import com.alexkn.syntact.service.SyntactService
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class DeckCreationViewModelTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    private var viewModel: DeckCreationViewModel

    @Inject
    lateinit var syntactService: SyntactService

    init {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val app = DaggerTestApplicationComponent.builder().applicationContext(context).build()
        app.inject(this)
        viewModel = app.createTemplateViewModelFactory().create(DeckCreationViewModel::class.java)
    }

    @Before
    fun setUp() {

        mockkStatic(Log::class)
        every { Log.e(any(), any(), any()) } answers {
            (invocation.args[2] as Exception).printStackTrace()
            0
        }

        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
        testCoroutineScope.cleanupTestCoroutines()
    }

    @Test
    fun `Suggestions are fetched correctly`() {

        coEvery { syntactService.getSuggestionSentences(any(), any(), any(), any()) } answers {
            PhraseSuggestionResponse(listOf(
                    Suggestion(src = "Haus", dest = "House", srcLang = Locale.GERMAN, destLang = Locale.ENGLISH)
            ))
        }

        viewModel.setLang("de")
        viewModel.fetchSuggestions(1, "Haus", Locale.GERMAN)

        val suggestions = viewModel.suggestions.value

        assertThat(suggestions).isNotNull().hasSize(1)
        val (id, keywordId, src, dest, srcLang, destLang) = suggestions!!.values.flatten()[0]
        assertThat(id).isNotNull()
        assertThat(keywordId).isNotNull()
        assertThat(src).isEqualTo("Haus")
        assertThat(dest).isEqualTo("House")
        assertThat(srcLang).isEqualTo(Locale.GERMAN)
        assertThat(destLang).isEqualTo(Locale.ENGLISH)
    }

    @Test
    fun `Exception is caught when call not successful`() {

        coEvery { syntactService.getSuggestionSentences(any(), any(), any(), any()) } throws RuntimeException()

        viewModel.setLang("de")
        viewModel.fetchSuggestions(1, "Test", Locale.ENGLISH)

        val suggestions = viewModel.suggestions.value

        assertThat(suggestions).hasSize(1)
        assertThat(suggestions!!.values.flatten()).isEmpty()
    }
}
