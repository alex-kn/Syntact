package dev.alexknittel.syntact.app

import dev.alexknittel.syntact.app.general.config.DaggerAndroidTestApplicationComponent

class SyntactTestApp : _root_ide_package_.dev.alexknittel.syntact.app.SyntactApplication() {

    override fun onCreate() {
        super.onCreate()
        applicationComponent = _root_ide_package_.dev.alexknittel.syntact.app.general.config.DaggerAndroidTestApplicationComponent.builder().applicationContext(this).build()
    }
}