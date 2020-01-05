package com.alexkn.syntact.core.repository

import com.alexkn.syntact.service.PhraseSuggestionResponse
import com.alexkn.syntact.service.SyntactService
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhraseSuggestionRepository @Inject constructor(
        private val syntactService: SyntactService
) {

    suspend fun fetchSuggestions(text: String, srcLang: Locale, destLang: Locale): List<PhraseSuggestionResponse> {
        return syntactService.getPhraseSuggestions("NYI", text, srcLang.language, destLang.language)
    }

}
