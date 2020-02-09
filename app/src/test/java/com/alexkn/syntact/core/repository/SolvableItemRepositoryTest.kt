package com.alexkn.syntact.core.repository

import com.alexkn.syntact.data.dao.SolvableItemDao
import com.alexkn.syntact.data.model.Clue
import com.alexkn.syntact.data.model.SolvableItem
import com.alexkn.syntact.data.model.SolvableTranslationCto
import com.nhaarman.mockito_kotlin.*
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
            if (i == 5) {
                solvableItemRepository.markPhraseIncorrect(solvableTranslation)
            } else {
                solvableItemRepository.markPhraseCorrect(solvableTranslation)

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