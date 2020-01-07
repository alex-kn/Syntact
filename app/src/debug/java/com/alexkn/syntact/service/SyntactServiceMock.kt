package com.alexkn.syntact.service

import android.util.Log
import com.alexkn.syntact.app.TAG
import com.alexkn.syntact.data.common.TemplateType
import okhttp3.Response
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

    override suspend fun getTranslations(token: String, url: String, lang: String): List<TranslationResponse> {
        Log.i(TAG, "getTranslations: Returning mocked response (URL $url, lang $lang)")
        return when (url) {
            "Hallo" -> listOf(TranslationResponse(id = 1, text = "Hello", language = Locale.ENGLISH))
            "Bier" -> listOf(TranslationResponse(id = 2, text = "Beer", language = Locale.ENGLISH))
            "Haus" -> listOf(TranslationResponse(id = 3, text = "House", language = Locale.ENGLISH))
            else -> throw IllegalArgumentException("URL not configured")
        }
    }

    override suspend fun getPhrases(token: String, url: String): List<PhraseResponse> {
        Log.i(TAG, "getPhrases: Returning mocked response (URL $url)")
        return listOf(
                PhraseResponse(id = 1, language = Locale.GERMAN, text = "Hallo", translationsUrl = "Hallo"),
                PhraseResponse(id = 2, language = Locale.GERMAN, text = "Bier", translationsUrl = "Bier"),
                PhraseResponse(id = 3, language = Locale.GERMAN, text = "Haus", translationsUrl = "Haus")
        )
    }

    override suspend fun getPhraseSuggestions(token: String, phrase: String, srcLang: String, destLang: String): List<PhraseSuggestionResponse> {
        Log.i(TAG, "SyntactServiceMock: Returning mocked PhraseSuggestionResponse")

        return listOf(
                PhraseSuggestionResponse(id = 1, src = "Hallo", dest = "Hello", srcLang = Locale.GERMAN, destLang = Locale.ENGLISH),
                PhraseSuggestionResponse(id = 2, src = "Bier", dest = "Beer", srcLang = Locale.GERMAN, destLang = Locale.ENGLISH),
                PhraseSuggestionResponse(id = 3, src = "Haus", dest = "House", srcLang = Locale.GERMAN, destLang = Locale.ENGLISH)
        )
    }

    override suspend fun postTemplate(template: TemplateRequest) {
        Log.i(TAG, "SyntactServiceMock: Posting $template")
    }
}