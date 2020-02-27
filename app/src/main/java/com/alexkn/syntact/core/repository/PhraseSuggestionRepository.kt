package com.alexkn.syntact.core.repository

import android.util.Log
import com.alexkn.syntact.app.AuthProv
import com.alexkn.syntact.app.TAG
import com.alexkn.syntact.service.Suggestion
import com.alexkn.syntact.service.SyntactService
import retrofit2.HttpException
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
        return try {
            syntactService.getPhraseSuggestions("Bearer $token", text, srcLang.language, destLang.language).suggestions
        } catch (he: HttpException) {
            Log.e(TAG, "PhraseSuggestionRepository: fetchSuggestions", he)
            emptyList()
        }
    }

}
