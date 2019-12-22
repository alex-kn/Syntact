package com.alexkn.syntact.app

class SyntactTestApp : SyntactApplication() {

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerTestApplicationComponent.builder().applicationContext(this).build()
    }
}