package com.alexkn.syntact.presentation.createtemplate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexkn.syntact.core.repository.PhraseSuggestionRepository
import com.alexkn.syntact.core.repository.TemplateRepository
import com.alexkn.syntact.service.PhraseSuggestionResponse
import kotlinx.coroutines.async
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

    fun fetchSuggestions(text: String) = viewModelScope.launch {
        _suggestions.postValue(_suggestions.value!! + phraseSuggestionRepository.fetchSuggestions(text, srcLang, destLang))
    }


}