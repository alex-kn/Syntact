package com.alexkn.syntact.core.repository

import com.alexkn.syntact.data.dao.SolvableItemDao
import com.alexkn.syntact.data.model.Clue
import com.alexkn.syntact.data.model.SolvableItem
import com.alexkn.syntact.data.model.SolvableTranslationCto
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.time.Instant

@ExperimentalCoroutinesApi
class SolvableItemRepositoryTest {

    private val daoMock: SolvableItemDao = mock()
    private var solvableItemRepository = SolvableItemRepository(daoMock)

    @Test
    fun correctSolution() = runBlockingTest {

        val solvableItem = SolvableItem(id = 1, text = "Text", deckId = 1)
        val clue = Clue(id = 1, text = "Text", solvableItemId = 1)

        val solvableTranslation = SolvableTranslationCto(solvableItem, clue)


        for (i in 1..10) {

            if (i < 4) {
                solvableItemRepository.markPhraseIncorrect(solvableTranslation, 0.3)
            } else {

                solvableItemRepository.markPhraseCorrect(solvableTranslation, 1.0)
            }

            argumentCaptor<SolvableItem>().apply {
                verify(daoMock, atLeastOnce()).update(capture())
                solvableTranslation.solvableItem = solvableItem
                println("$solvableItem")
                solvableItem.nextDueDate = Instant.now()
            }
        }

    }
}