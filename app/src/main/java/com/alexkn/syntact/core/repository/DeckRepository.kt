package com.alexkn.syntact.core.repository

import androidx.lifecycle.LiveData
import com.alexkn.syntact.app.Property
import com.alexkn.syntact.data.dao.DeckDao
import com.alexkn.syntact.data.model.*
import com.alexkn.syntact.service.Suggestion
import com.alexkn.syntact.service.SyntactService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeckRepository @Inject constructor(
        private val syntactService: SyntactService,
        private val property: Property,
        private val deckDao: DeckDao
) {

    val availableLanguages: MutableList<Locale> = property["available-languages"].split(",").map { Locale(it) }.toMutableList()

    suspend fun addBucketWithExistingTemplate(template: Template) = withContext(Dispatchers.Default) {

        val sourceLanguage = Locale.getDefault()

        val deck = Deck(name = template.name, language = template.language, userLanguage = sourceLanguage, itemCount = template.count, newItemsPerDay = 20)
        val token = "Token " + property["api-auth-token"]

        val phrases = syntactService.getPhrases(token, template.phrasesUrl)

        val solvableTranslations = phrases.map {
            SolvableTranslationCto(SolvableItem(text = it.text), Clue(text = it.translations[0].text))
        }

        deckDao.insert(deck, solvableTranslations)
    }

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

    fun findAllLive(): LiveData<List<Deck>> = deckDao.findAllLiveData()

    fun findDeck(id: Long): LiveData<Deck?> = deckDao.findLive(id)

    suspend fun find(id: Long): Deck = deckDao.find(id)
}
