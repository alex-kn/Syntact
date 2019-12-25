package com.alexkn.syntact.app

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthProv @Inject constructor() {

    fun requestToken(): String? {
        Log.i(TAG, "requestToken: MockToken requested")
        return "abc"
    }
}