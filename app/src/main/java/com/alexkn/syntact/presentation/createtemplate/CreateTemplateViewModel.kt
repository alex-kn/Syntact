package com.alexkn.syntact.presentation.createtemplate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexkn.syntact.domain.repository.TemplateRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateTemplateViewModel @Inject constructor(
        private val templateRepository: TemplateRepository
) : ViewModel() {

    fun createFromText(text: String) {

        viewModelScope.launch {
            templateRepository.postTemplate(text)
        }
    }
}