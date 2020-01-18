package com.alexkn.syntact.core.repository

import com.alexkn.syntact.app.AuthProv
import com.alexkn.syntact.app.Property
import com.alexkn.syntact.service.PhraseSuggestionResponse
import com.alexkn.syntact.service.Suggestion
import com.alexkn.syntact.service.SyntactService
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhraseSuggestionRepository @Inject constructor(
        private val syntactService: SyntactService,
        private val authenticationProvider: AuthProv
) {

    suspend fun fetchSuggestions(text: String, srcLang: Locale, destLang: Locale): List<Suggestion> {
        val token = authenticationProvider.requestToken()
        return syntactService.getPhraseSuggestions("Bearer $token", text, srcLang.language, destLang.language).suggestions
    }

}
