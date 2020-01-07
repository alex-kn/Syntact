package com.alexkn.syntact.presentation.createtemplate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexkn.syntact.core.repository.PhraseSuggestionRepository
import com.alexkn.syntact.core.repository.TemplateRepository
import com.alexkn.syntact.service.PhraseSuggestionResponse
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CreateTemplateViewModel @Inject constructor(
        private val templateRepository: TemplateRepository,
        private val phraseSuggestionRepository: PhraseSuggestionRepository
) : ViewModel() {

    private val srcLang = Locale.GERMAN
    private val destLang = Locale.ENGLISH
    private val _suggestions = MutableLiveData<List<PhraseSuggestionResponse>>(listOf())

    val suggestions: LiveData<List<PhraseSuggestionResponse>>
        get() = _suggestions

    fun fetchSuggestions(keywordId: Int, text: String) = viewModelScope.launch {
        val newSuggestions = phraseSuggestionRepository.fetchSuggestions(text, srcLang, destLang)
        newSuggestions.forEach { it.keywordId = keywordId }
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