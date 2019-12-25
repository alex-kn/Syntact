package com.alexkn.syntact.service

import android.util.Log
import com.alexkn.syntact.app.TAG
import com.alexkn.syntact.data.common.TemplateType
import com.alexkn.syntact.service.to.*
import okhttp3.ResponseBody
import retrofit2.Call
import java.util.*

class SyntactServiceMock : SyntactService {

    override suspend fun getTemplates(token: String): List<TemplateResponse> {
        Log.i(TAG, "getTemplates: Returning mocked response")
        return listOf(
                TemplateResponse(id = 1, name = "Mocked Template Response", templateType = TemplateType.BASIC, language = Locale.GERMAN, phrasesUrl = "phrasesUrl", count = 3, description = "Description")
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

    override fun postPhrases(token: String, templateId: Long?, lang: String, phrasesRequests: List<PhrasesRequest>): Call<ResponseBody> {
        Log.i(TAG, "postPhrases: Returning mocked response")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun postTemplate(token: String, templateRequest: TemplateRequest): ResponseBody {
        Log.i(TAG, "postTemplate: Returning mocked response")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteTemplate(token: String, templateId: Long?): Call<ResponseBody> {
        Log.i(TAG, "deleteTemplate: Returning mocked response")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}