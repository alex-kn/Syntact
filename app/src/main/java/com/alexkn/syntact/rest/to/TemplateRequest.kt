package com.alexkn.syntact.rest.to

import java.util.*

data class TemplateRequest(
        var name: String,
        var description: String,
        var source: Locale,
        var target: Locale,
        var words: List<String>? = null,
        var text: String? = null
)

