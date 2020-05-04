package com.alexkn.syntact.service.config

import com.alexkn.syntact.service.PhraseSuggestionResponse
import com.alexkn.syntact.service.Suggestion
import com.alexkn.syntact.service.SyntactService
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
class AndroidTestNetworkModule {

    @Singleton
    @Provides
    fun provideSyntactService(): SyntactService = object : SyntactService {
        override suspend fun getSuggestionSentences(token: String, phrase: String, srcLang: String, destLang: String): PhraseSuggestionResponse {
            return PhraseSuggestionResponse(listOf(Suggestion(src = "Das ist ein Haus", dest = "This is a House", srcLang = Locale.GERMAN, destLang = Locale.ENGLISH)))
        }
    }

}
