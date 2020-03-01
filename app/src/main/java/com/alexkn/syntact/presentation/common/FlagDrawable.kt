package com.alexkn.syntact.presentation.common

import com.alexkn.syntact.R
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlagDrawable @Inject constructor() {

    operator fun get(locale: Locale): Int {

        return when (locale) {
            Locale.GERMAN -> R.drawable.de
            Locale.ENGLISH -> R.drawable.en
            else -> throw UnsupportedLanguageException(locale)
        }
    }

    inner class UnsupportedLanguageException(locale: Locale) : RuntimeException("Locale $locale ist not supported")
}

