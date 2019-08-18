package com.alexkn.syntact.rest.to

import java.util.Locale

data class TranslationResponse(
        var id: Long,
        var text: String,
        var language: Locale
)


