package com.alexkn.syntact.core.repository

import androidx.lifecycle.LiveData
import com.alexkn.syntact.app.Property
import com.alexkn.syntact.data.dao.DeckDao
import com.alexkn.syntact.data.dao.PlayerStatsDao
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
        private val deckDao: DeckDao,
        private val playerStatsDao: PlayerStatsDao
) {

    val availableLanguages: MutableList<Locale> = property["available-languages"].split(",").map { Locale(it) }.toMutableList()

    val deckDetails: LiveData<List<DeckDetail>>
        get() = deckDao.findBucketDetails()

    suspend fun addBucketWithExistingTemplate(template: Template) = withContext(Dispatchers.Default) {

        val sourceLanguage = Locale.getDefault()

        val deck = Deck(name = template.name, language = template.language, userLanguage = sourceLanguage, itemCount = template.count)
        val token = "Token " + property["api-auth-token"]

        val phrases = syntactService.getPhrases(token, template.phrasesUrl)

        val solvableTranslations = phrases.map {
            SolvableTranslationCto(SolvableItem(text = it.text), Clue(text = it.translations[0].text))
        }

        deckDao.insert(deck, solvableTranslations)
    }

    suspend fun createNewDeck(name: String, deckLang: Locale, phrases: List<Suggestion>) {
        val deck = Deck(name = name, language = deckLang, userLanguage = Locale.getDefault(), itemCount = phrases.size)
        val solvableTranslations = phrases.map { phrase ->
            when (deckLang) {
                phrase.srcLang -> SolvableTranslationCto(SolvableItem(text = phrase.src), Clue(text = phrase.dest))
                else -> SolvableTranslationCto(SolvableItem(text = phrase.dest), Clue(text = phrase.src))
            }
        }
        deckDao.insert(deck, solvableTranslations)
    }

    suspend fun deleteBucket(id: Long) {
        deckDao.delete(id)
    }

    suspend fun findAll(): List<Deck> {
        return deckDao.findAll()
    }

    fun findDeck(id: Long): LiveData<Deck?> {
        return deckDao.findLive(id)
    }

    fun getBucketDetail(id: Long): LiveData<DeckDetail> {
        return deckDao.findBucketDetail(id)
    }

    fun getPlayerStats(): LiveData<PlayerStats> {
        return playerStatsDao.find()
    }
}
