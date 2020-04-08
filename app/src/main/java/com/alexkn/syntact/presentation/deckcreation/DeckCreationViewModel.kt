package com.alexkn.syntact.presentation.deckcreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexkn.syntact.app.Property
import com.alexkn.syntact.core.repository.DeckRepository
import com.alexkn.syntact.core.repository.PhraseSuggestionRepository
import com.alexkn.syntact.core.repository.PreferencesRepository
import com.alexkn.syntact.data.model.Preferences
import com.alexkn.syntact.presentation.common.handler
import com.alexkn.syntact.service.Suggestion
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class DeckCreationViewModel @Inject constructor(
        private val deckRepository: DeckRepository,
        private val phraseSuggestionRepository: PhraseSuggestionRepository,
        private val preferencesRepository: PreferencesRepository,
        private val property: Property
) : ViewModel() {

    private val _suggestions = MutableLiveData<Map<Int, List<Suggestion>>>(emptyMap())

    private var idSequence = 1L

    private lateinit var prefs: Preferences

    val defaultNewCardsPerDay = 20

    var languageChoices: MutableLiveData<List<Locale>> = MutableLiveData(emptyList())

    private val _deckLang = MutableLiveData<Locale?>()
    val deckLang: LiveData<Locale?>
        get() = _deckLang

    val userLang = MutableLiveData<Locale?>()

    private val _deckName = MutableLiveData<String?>()
    val deckName: LiveData<String?>
        get() = _deckName

    val suggestions: LiveData<Map<Int, List<Suggestion>>>
        get() = _suggestions

    val generateFullSentences = MutableLiveData(true)

    val generateRelatedWords = MutableLiveData(false)

    val numberOfGeneratedItems = MutableLiveData(NumberOfItems.LOW)

    init {
        viewModelScope.launch {
            prefs = preferencesRepository.find()
            userLang.postValue(prefs.language)
            val choices = property["available-languages"].split(',').map { Locale(it) }.filterNot { it == prefs.language }
            languageChoices.postValue(choices)
        }
    }

    fun setLang(lang: String) {
        _deckLang.postValue(Locale(lang))
    }

    fun fetchSuggestions(keywordId: Int, text: String, suggestionLang: Locale) = viewModelScope.launch(handler()) {
        val deckLangVal = deckLang.value!!
        val destLang = if (suggestionLang == deckLangVal) prefs.language else deckLangVal
        var newSuggestions = phraseSuggestionRepository.fetchSuggestions(text, suggestionLang, destLang, generateFullSentences.value!!, generateRelatedWords.value!!, numberOfGeneratedItems.value!!.value)
        if (suggestionLang != deckLangVal) newSuggestions = swapLanguage(newSuggestions)
        newSuggestions.forEach { it.keywordId = keywordId; it.id = idSequence++ }
        addNewSuggestions(keywordId, newSuggestions)
    }

    @Synchronized
    private fun addNewSuggestions(keywordId: Int, suggestionList: List<Suggestion>) {
        val currentSuggestions = _suggestions.value!!
        val newSuggestions = suggestionList.filterNot { new ->
            currentSuggestions.values.flatten().any { old ->
                0 == compareValuesBy(old, new, { it.src }, { it.dest }, { it.srcLang.language }, { it.destLang.language })
            }
        }
        val newSuggestionMap = currentSuggestions + mapOf(keywordId to newSuggestions)
        _suggestions.postValue(newSuggestionMap)
    }

    private fun swapLanguage(newSuggestions: List<Suggestion>): List<Suggestion> {
        return newSuggestions.map { Suggestion(srcLang = it.destLang, destLang = it.srcLang, src = it.dest, dest = it.src) }
    }

    fun removeItem(suggestion: Suggestion) {
        val suggestions = _suggestions.value!!.toMutableMap()
        val filteredSuggestions = suggestions[suggestion.keywordId]!!.filterNot { it.id == suggestion.id }
        suggestions.replace(suggestion.keywordId!!, filteredSuggestions)
        _suggestions.postValue(suggestions)
    }

    fun removeKeyword(keywordId: Int) {
        val newSuggestions = _suggestions.value!!.toMutableMap().filter { it.key != keywordId }
        _suggestions.postValue(newSuggestions)
    }

    fun createDeck(maxCardsPerDay: String) {
        val maxItemsPerDayInput = maxCardsPerDay.toIntOrNull() ?: 0
        val maxItemsPerDay = when {
            maxItemsPerDayInput < 1 -> 1
            maxItemsPerDayInput > 1000 -> 1000
            else -> maxItemsPerDayInput
        }
        val phrases = _suggestions.value!!.toSortedMap().values.flatten()
        val distinctPhrases = phrases.distinctBy { item -> item.copy(keywordId = null, id = null) }
        GlobalScope.launch {
            deckRepository.createNewDeck(
                    _deckName.value!!,
                    deckLang.value!!,
                    prefs.language,
                    distinctPhrases,
                    maxItemsPerDay
            )
        }
    }

    fun switchFullSentences() {
        generateFullSentences.value = !generateFullSentences.value!!
    }

    fun switchRelatedWords() {
        generateRelatedWords.value = !generateRelatedWords.value!!
    }

    fun switchNumberOfGeneratedItems() {
        numberOfGeneratedItems.value = numberOfGeneratedItems.value!!.next()
    }

    fun switchDeckLang(index: Int) {
        _suggestions.value = emptyMap()
        _deckLang.value = languageChoices.value!![index]
    }

    fun setDeckName(name: String) {
        _deckName.value = name
    }
}

