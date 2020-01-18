package com.alexkn.syntact.presentation.deckcreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexkn.syntact.core.repository.PhraseSuggestionRepository
import com.alexkn.syntact.core.repository.TemplateRepository
import com.alexkn.syntact.service.Suggestion
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class DeckCreationViewModel @Inject constructor(
        private val templateRepository: TemplateRepository,
        private val phraseSuggestionRepository: PhraseSuggestionRepository
) : ViewModel() {

    private val srcLang = Locale.GERMAN
    private val destLang = Locale.ENGLISH
    private val _suggestions = MutableLiveData<List<Suggestion>>(listOf())

    private var idSequence = 1L;

    val suggestions: LiveData<List<Suggestion>>
        get() = _suggestions

    fun fetchSuggestions(keywordId: Int, text: String) = viewModelScope.launch {
        val newSuggestions = phraseSuggestionRepository.fetchSuggestions(text, srcLang, destLang)
        newSuggestions.forEach { it.keywordId = keywordId; it.id = idSequence++ }
        _suggestions.postValue((_suggestions.value!! + newSuggestions).sortedBy { it.id })
    }

    fun removeKeyword(keywordId: Int) {
        val newSuggestions = _suggestions.value!!.toMutableList().filter { keywordId != it.keywordId }
        _suggestions.postValue(newSuggestions)
    }

    fun createTemplate() = viewModelScope.launch {
        templateRepository.createNewTemplate(_suggestions.value!!)
    }


}