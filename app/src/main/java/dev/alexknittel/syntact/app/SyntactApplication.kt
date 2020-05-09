package dev.alexknittel.syntact.app

import android.app.Application
import dev.alexknittel.syntact.BuildConfig
import dev.alexknittel.syntact.app.general.config.ApplicationComponent
import dev.alexknittel.syntact.app.general.config.ApplicationComponentProvider
import dev.alexknittel.syntact.app.general.config.DaggerApplicationComponent

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