package com.alexkn.syntact.service.to

import java.util.Locale

data class TranslationResponse(
        var id: Long,
        var text: String,
        var language: Locale
)


