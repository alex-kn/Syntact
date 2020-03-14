package com.alexkn.syntact.app

import android.app.Application

open class SyntactApplication : Application(), ApplicationComponentProvider {

    override lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {

        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder().applicationContext(this).build()
    }
}
