package com.alexkn.syntact.restservice

import java.util.Locale
import java.util.Objects

data class Translation(
        var id: Long,
        var text: String,
        var language: Locale
)


