package com.alexkn.syntact.service

import com.alexkn.syntact.core.model.Identifiable
import java.util.*


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
