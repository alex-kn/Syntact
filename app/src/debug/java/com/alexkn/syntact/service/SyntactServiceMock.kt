package com.alexkn.syntact.service

import android.util.Log
import com.alexkn.syntact.app.TAG
import com.alexkn.syntact.data.common.TemplateType
import java.util.*

class SyntactServiceMock : SyntactService {

    override suspend fun getTemplates(token: String): List<TemplateResponse> {
        Log.i(TAG, "getTemplates: Returning mocked response")
        return listOf(
                TemplateResponse(
                        id = 1,
                        name = "Mocked Template Response",
                        templateType = TemplateType.BASIC,
                        language = Locale.GERMAN,
                        phrasesUrl = "phrasesUrl",
                        count = 3,
                        description = "Description"
                )
        )
    }

    override suspend fun getPhrases(token: String, url: String): List<PhraseResponse> {
        Log.i(TAG, "getPhrases: Returning mocked response (URL $url)")
        return listOf(
                PhraseResponse(id = 1, language = Locale.GERMAN, text = "Hallo", translationsUrl = "Hallo", translations = listOf(TranslationResponse(id = 1, text = "Hello", language = Locale.ENGLISH))),
                PhraseResponse(id = 2, language = Locale.GERMAN, text = "Bier", translationsUrl = "Bier", translations = listOf(TranslationResponse(id = 2, text = "Beer", language = Locale.ENGLISH))),
                PhraseResponse(id = 3, language = Locale.GERMAN, text = "Haus", translationsUrl = "Haus", translations = listOf(TranslationResponse(id = 3, text = "House", language = Locale.ENGLISH)))
        )
    }

    override suspend fun getSuggestionSentences(token: String, phrase: String, srcLang: String, destLang: String): PhraseSuggestionResponse {
        Log.i(TAG, "SyntactServiceMock: Returning mocked Sentences")

        return PhraseSuggestionResponse(listOf(
                Suggestion(id = 1, src = "Hallo", dest = "Hello", srcLang = Locale.GERMAN, destLang = Locale.ENGLISH),
                Suggestion(id = 2, src = "Bier", dest = "Beer", srcLang = Locale.GERMAN, destLang = Locale.ENGLISH),
                Suggestion(id = 3, src = "Haus", dest = "House", srcLang = Locale.GERMAN, destLang = Locale.ENGLISH)
        ))
    }

    override suspend fun getSuggestionWords(token: String, phrase: String, srcLang: String, destLang: String, volume: Int): PhraseSuggestionResponse {
        Log.i(TAG, "SyntactServiceMock: Returning mocked Words")

        return PhraseSuggestionResponse(listOf(
                Suggestion(id = 1, src = "Hallo", dest = "Hello", srcLang = Locale.GERMAN, destLang = Locale.ENGLISH),
                Suggestion(id = 2, src = "Bier", dest = "Beer", srcLang = Locale.GERMAN, destLang = Locale.ENGLISH),
                Suggestion(id = 3, src = "Haus", dest = "House", srcLang = Locale.GERMAN, destLang = Locale.ENGLISH)
        ))
    }

    override suspend fun postTemplate(template: TemplateRequest): TemplateResponse {
        Log.i(TAG, "SyntactServiceMock: Posting $template")
        TODO()
    }
}