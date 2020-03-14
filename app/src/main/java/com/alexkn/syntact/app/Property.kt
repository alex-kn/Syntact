package com.alexkn.syntact.app

import android.content.Context
import android.util.Log
import com.alexkn.syntact.R
import java.io.IOException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Property @Inject constructor(context: Context) {

    private lateinit var properties: Properties

    init {
        try {
            val resources = context.resources
            val inputStream = resources.openRawResource(R.raw.config)

            properties = Properties()
            properties.load(inputStream)
        } catch (e: IOException) {
            Log.e(TAG, "loadProperties: Unable to open config file.", e)
        }

    }

    operator fun get(key: String): String = properties.getProperty(key)
}
