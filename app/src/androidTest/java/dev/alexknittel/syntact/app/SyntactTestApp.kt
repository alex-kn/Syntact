package dev.alexknittel.syntact.app

import dev.alexknittel.syntact.app.general.config.DaggerAndroidTestApplicationComponent

class SyntactTestApp : SyntactApplication() {

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerAndroidTestApplicationComponent.builder().applicationContext(this).build()
    }
}