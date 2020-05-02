package com.alexkn.syntact.app

import android.app.Application
import com.alexkn.syntact.BuildConfig
import com.alexkn.syntact.app.general.config.ApplicationComponent
import com.alexkn.syntact.app.general.config.ApplicationComponentProvider
import com.alexkn.syntact.app.general.config.DaggerApplicationComponent

open class SyntactApplication : Application(), ApplicationComponentProvider {

    override lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        System.setProperty("kotlinx.coroutines.debug", if (BuildConfig.DEBUG) "on" else "off")
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder().applicationContext(this).build()
    }
}

val Any.TAG: String
    get() = javaClass.simpleName