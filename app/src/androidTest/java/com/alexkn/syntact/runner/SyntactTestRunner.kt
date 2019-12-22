package com.alexkn.syntact.runner

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.alexkn.syntact.app.SyntactTestApp

class SyntactTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, SyntactTestApp::class.java.name, context)
    }

}