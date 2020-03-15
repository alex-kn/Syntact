package com.alexkn.syntact.app

import android.content.Context
import android.util.Log
import java.io.IOException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Property @Inject constructor(context: Context) {

    private var properties = Properties()

    init {
        try {
            context.assets.open("config.properties").use {
                properties.load(it)
            }
        } catch (e: IOException) {
            Log.e(TAG, "loadProperties: Unable to open config file.", e)
        }

    }

    operator fun get(key: String): String = properties.getProperty(key)
}
