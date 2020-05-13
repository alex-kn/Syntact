package dev.alexknittel.syntact.core.repository

import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ConfigRepository @Inject constructor() {

    var availableLanguages = listOf(
            Locale.ENGLISH,
            Locale.GERMAN,
            Locale("es"),
            Locale.FRENCH
    )
        private set

}