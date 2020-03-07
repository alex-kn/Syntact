package com.alexkn.syntact.presentation.deckcreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexkn.syntact.core.repository.DeckRepository
import com.alexkn.syntact.core.repository.PhraseSuggestionRepository
import com.alexkn.syntact.core.repository.PreferencesRepository
import com.alexkn.syntact.data.model.Preferences
import com.alexkn.syntact.presentation.common.FlagDrawable
import com.alexkn.syntact.service.Suggestion
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class DeckCreationViewModel @Inject constructor(
        private val deckRepository: DeckRepository,
        private val phraseSuggestionRepository: PhraseSuggestionRepository,
        private val flagDrawable: FlagDrawable,
        private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private var _suggestionLang: MutableLiveData<Locale?> = MutableLiveData()
    private val _suggestions = MutableLiveData<Map<Int, List<Suggestion>>>(emptyMap())

    private var idSequence = 1L

    lateinit var prefs: Preferences

    private val _deckLang = MutableLiveData<Locale?>()
    val deckLang: LiveData<Locale?>
        get() = _deckLang

    private val _deckName = MutableLiveData<String?>()
    val deckName: LiveData<String?>
        get() = _deckName

    val suggestionFlag: Int
        get() = flagDrawable[suggestionLang.value!!]

    val suggestionLang: LiveData<Locale?>
        get() = _suggestionLang

    val suggestions: LiveData<Map<Int, List<Suggestion>>>
        get() = _suggestions

    init {
        viewModelScope.launch {
            prefs = preferencesRepository.find()
        }
        _deckLang.value = Locale.GERMAN
        _suggestionLang.value = Locale.GERMAN
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

    fun createDeck(): Boolean {
        if (_deckName.value!!.isBlank()) return false
        GlobalScope.launch {
            deckRepository.createNewDeck(
                    _deckName.value!!,
                    deckLang.value!!,
                    prefs.language,
                    _suggestions.value!!.toSortedMap().values.flatten()
            )
        }
        return true
    }

    fun switchDeckLang(locale: Locale) {
        _deckLang.value = locale
    }

    fun setDeckName(name: String) {
        _deckName.value = name
    }

    fun switchSuggestionLang() {
        _suggestionLang.value = if (suggestionLang.value == deckLang.value!!) prefs.language else deckLang.value!!
    }

}

