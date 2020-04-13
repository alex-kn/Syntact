package com.alexkn.syntact.core.repository

import com.alexkn.syntact.app.AuthProv
import com.alexkn.syntact.service.SyntactService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhraseSuggestionRepository @Inject constructor(
        private val syntactService: SyntactService,
        private val authenticationProvider: AuthProv
) {

    suspend fun fetchSuggestions(text: String, srcLang: Locale, destLang: Locale) = coroutineScope {
        val token = authenticationProvider.requestToken()

        val sentences = async {
            syntactService.getSuggestionSentences("Bearer $token", text, srcLang.language, destLang.language).suggestions
        }

        sentences.await()
    }

}
