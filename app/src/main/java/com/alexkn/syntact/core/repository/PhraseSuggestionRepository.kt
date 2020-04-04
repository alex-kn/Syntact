package com.alexkn.syntact.core.repository

import android.util.Log
import com.alexkn.syntact.app.AuthProv
import com.alexkn.syntact.app.TAG
import com.alexkn.syntact.service.Suggestion
import com.alexkn.syntact.service.SyntactService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhraseSuggestionRepository @Inject constructor(
        private val syntactService: SyntactService,
        private val authenticationProvider: AuthProv
) {

    suspend fun fetchSuggestions(text: String, srcLang: Locale, destLang: Locale, generateFullSentences: Boolean, generateRelatedWords: Boolean, volume: Int) = coroutineScope<List<Suggestion>> {
        val token = authenticationProvider.requestToken()
        try {

            val sentences = async {
                if (generateFullSentences) {
                    syntactService.getSuggestionSentences("Bearer $token", text, srcLang.language, destLang.language).suggestions
                } else emptyList()
            }

            val words = async {
                if (generateRelatedWords) {
                    syntactService.getSuggestionWords("Bearer $token", text, srcLang.language, destLang.language, volume).suggestions
                } else emptyList()
            }

            words.await() + sentences.await()

        } catch (he: HttpException) {
            Log.e(TAG, "PhraseSuggestionRepository: fetchSuggestions", he)
            emptyList()
        } catch (he: SocketTimeoutException) {
            Log.e(TAG, "PhraseSuggestionRepository: fetchSuggestions", he)
            emptyList()
        }
    }

}
