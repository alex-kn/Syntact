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

    private var _suggestionLang: MutableLiveData<Locale?> = MutableLiveData()
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

    val suggestionLang: LiveData<Locale?>
        get() = _suggestionLang

    val suggestions: LiveData<Map<Int, List<Suggestion>>>
        get() = _suggestions

    init {
        viewModelScope.launch {
            prefs = preferencesRepository.find()
            userLang.postValue(prefs.language)
            val choices = property["available-languages"].split(',').map { Locale(it) }.filterNot { it == prefs.language }
            languageChoices.postValue(choices)
            _suggestionLang.postValue(choices.first())
            _deckLang.postValue(choices.first())
        }
    }

    fun fetchSuggestions(keywordId: Int, text: String) = viewModelScope.launch {
        val srcLangVal = _suggestionLang.value!!
        val deckLangVal = deckLang.value!!
        val destLang = if (srcLangVal == deckLangVal) prefs.language else deckLangVal
        var newSuggestions = phraseSuggestionRepository.fetchSuggestions(text, srcLangVal, destLang)
        if (srcLangVal != deckLangVal) newSuggestions = newSuggestions.map { Suggestion(srcLang = it.destLang, destLang = it.srcLang, src = it.dest, dest = it.src) }
        newSuggestions.forEach { it.keywordId = keywordId; it.id = idSequence++ }

        val newSuggestionMap = _suggestions.value!! + mapOf(keywordId to newSuggestions)
        _suggestions.postValue(newSuggestionMap)
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

    fun switchDeckLang(index: Int) {
        _suggestions.value = emptyMap()
        _deckLang.value = languageChoices.value!![index]
    }

    fun setDeckName(name: String) {
        _deckName.value = name
    }

    fun switchSuggestionLangToDeckLang() {
        _suggestionLang.value = deckLang.value
    }

    fun switchSuggestionLangToUserLang() {
        _suggestionLang.value = userLang.value
    }
}

