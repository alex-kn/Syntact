package com.alexkn.syntact.core.repository

import android.content.Context
import android.util.Log
import com.alexkn.syntact.app.TAG
import java.io.IOException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ConfigRepository @Inject constructor(context: Context) {

    private var properties = Properties()

    var availableLanguages = listOf(
            Locale.ENGLISH,
            Locale.GERMAN,
            Locale("es"),
            Locale.ITALIAN,
            Locale.FRENCH,
            Locale("zh"),
            Locale("swe")
    )
        private set

    init {
        try {
            context.assets.open("app.properties").use {
                properties.load(it)
            }
        } catch (e: IOException) {
            Log.e(TAG, "loadProperties: Unable to open config file.", e)
        }
    }

    operator fun get(key: String): String = properties.getProperty(key)

    fun getBackendUrl(): String = properties.getProperty("backend_url")

}