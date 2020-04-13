package com.alexkn.syntact.app

import android.app.Application
import com.alexkn.syntact.BuildConfig

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