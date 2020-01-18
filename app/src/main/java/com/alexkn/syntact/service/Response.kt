package com.alexkn.syntact.service

import android.provider.ContactsContract
import com.alexkn.syntact.data.common.Identifiable
import com.alexkn.syntact.data.common.TemplateType
import com.google.gson.annotations.SerializedName

import java.util.Locale

data class PhraseResponse(
        var id: Long,
        var text: String,
        var language: Locale,
        @SerializedName("translations") var translationsUrl: String
)

data class TemplateResponse(
        override var id: Long,
        var name: String,
        @SerializedName("template_type") var templateType: TemplateType,
        var language: Locale,
        @SerializedName("phrases") var phrasesUrl: String,
        @SerializedName("phrases_count") var count: Int,
        var description: String
) : Identifiable<Long>

data class TranslationResponse(
        var id: Long,
        var text: String,
        var language: Locale
)

data class PhraseSuggestionResponse(
        var suggestions: List<Suggestion>
)

data class Suggestion(
        override var id: Long? = null,
        var keywordId: Int? = null,
        var src: String,
        var dest: String,
        var srcLang: Locale,
        var destLang: Locale
) : Identifiable<Long>
