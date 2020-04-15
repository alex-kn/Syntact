package com.alexkn.syntact.core.repository

import androidx.lifecycle.LiveData
import com.alexkn.syntact.core.model.Clue
import com.alexkn.syntact.core.model.Deck
import com.alexkn.syntact.core.model.SolvableItem
import com.alexkn.syntact.core.model.SolvableTranslationCto
import com.alexkn.syntact.data.dao.DeckDao
import com.alexkn.syntact.service.Suggestion
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeckRepository @Inject constructor(
        private val deckDao: DeckDao
) {

    suspend fun createNewDeck(name: String, deckLang: Locale, userLang: Locale, phrases: List<Suggestion>, maxCardsPerDay: Int) {
        val deck = Deck(name = name, language = deckLang, userLanguage = userLang, itemCount = phrases.size, newItemsPerDay = maxCardsPerDay)
        val solvableTranslations = phrases.map { phrase ->
            when (deckLang) {
                phrase.srcLang -> SolvableTranslationCto(SolvableItem(text = phrase.src), Clue(text = phrase.dest))
                else -> SolvableTranslationCto(SolvableItem(text = phrase.dest), Clue(text = phrase.src))
            }
        }
        deckDao.insert(deck, solvableTranslations)
    }

    suspend fun updateDeck(deck: Deck) = deckDao.update(deck)

    suspend fun deleteDeck(id: Long) = deckDao.delete(id)

    suspend fun findAll(): List<Deck> = deckDao.findAll()

    fun findDeck(id: Long): LiveData<Deck?> = deckDao.findLive(id)

    suspend fun find(id: Long): Deck = deckDao.find(id)

    suspend fun find(userLanguage: Locale) = deckDao.find(userLanguage)
}
