package dev.alexknittel.syntact.service

import dev.alexknittel.syntact.core.model.Identifiable
import java.util.*

data class Suggestion(
        override var id: Long? = null,
        var keywordId: Int? = null,
        var src: String,
        var dest: String,
        var srcLang: Locale,
        var destLang: Locale
) : Identifiable<Long>
