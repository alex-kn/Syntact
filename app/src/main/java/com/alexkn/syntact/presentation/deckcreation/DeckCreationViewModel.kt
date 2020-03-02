package com.alexkn.syntact.presentation.deckcreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexkn.syntact.core.repository.DeckRepository
import com.alexkn.syntact.core.repository.PhraseSuggestionRepository
import com.alexkn.syntact.presentation.common.FlagDrawable
import com.alexkn.syntact.service.Suggestion
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class DeckCreationViewModel @Inject constructor(
        private val deckRepository: DeckRepository,
        private val phraseSuggestionRepository: PhraseSuggestionRepository,
        private val flagDrawable: FlagDrawable
) : ViewModel() {

    private val deckLang = Locale.GERMAN
    private val userLang = Locale.ENGLISH

    private var _suggestionLang = MutableLiveData<Locale>(deckLang)
    private val _suggestions = MutableLiveData<Map<Int, List<Suggestion>>>(emptyMap())

    private var idSequence = 1L

    val defaultDeckName = deckLang.displayLanguage

    val suggestionFlag: Int
        get() = flagDrawable[suggestionLang.value!!]

    val suggestionLang: LiveData<Locale>
        get() = _suggestionLang

    val suggestions: LiveData<Map<Int, List<Suggestion>>>
        get() = _suggestions

    fun fetchSuggestions(keywordId: Int, text: String) = viewModelScope.launch {
        val srcLang = _suggestionLang.value!!
        val destLang = if (srcLang == deckLang) userLang else deckLang
        var newSuggestions = phraseSuggestionRepository.fetchSuggestions(text, srcLang, destLang)
        if (srcLang != deckLang) newSuggestions = newSuggestions.map { Suggestion(srcLang = it.destLang, destLang = it.srcLang, src = it.dest, dest = it.src) }
        newSuggestions.forEach { it.keywordId = keywordId; it.id = idSequence++ }

        val newSuggestionMap = _suggestions.value!! + mapOf(keywordId to newSuggestions)
        _suggestions.postValue(newSuggestionMap)
    }

    fun removeKeyword(keywordId: Int) {
        val newSuggestions = _suggestions.value!!.toMutableMap().filter { it.key != keywordId }
        _suggestions.postValue(newSuggestions)
    }

    fun createDeck(name: String) = GlobalScope.launch {
        deckRepository.createNewDeck(if (name.isBlank()) defaultDeckName else name, deckLang, _suggestions.value!!.toSortedMap().values.flatten())
    }

    fun switchSuggestionLang() {
        _suggestionLang.value = if (suggestionLang.value == deckLang) userLang else deckLang
    }

}