package com.alexkn.syntact.app

import com.alexkn.syntact.app.general.config.DaggerAndroidTestApplicationComponent

class SyntactTestApp : SyntactApplication() {

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerAndroidTestApplicationComponent.builder().applicationContext(this).build()
    }
}