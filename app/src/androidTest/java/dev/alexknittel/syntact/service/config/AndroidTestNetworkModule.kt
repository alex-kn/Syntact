package dev.alexknittel.syntact.service.config

import dagger.Module
import dagger.Provides
import dev.alexknittel.syntact.service.PhraseSuggestionResponse
import dev.alexknittel.syntact.service.Suggestion
import dev.alexknittel.syntact.service.SyntactService
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
