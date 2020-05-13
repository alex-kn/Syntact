package dev.alexknittel.syntact.app.general.config

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dev.alexknittel.syntact.app.SyntactTestApp

class SyntactTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, SyntactTestApp::class.java.name, context)
    }

}