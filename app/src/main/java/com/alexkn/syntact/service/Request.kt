package com.alexkn.syntact.service

import java.util.*

data class TemplateRequest(
        var phrases: List<PhraseRequest>
)

data class PhraseRequest(
        var src: String,
        var dest: String,
        var srcLang: Locale,
        var destLang: Locale
)
