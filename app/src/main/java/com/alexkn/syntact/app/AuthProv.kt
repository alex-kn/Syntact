package com.alexkn.syntact.app

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.lang.IllegalStateException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthProv @Inject constructor() {

    private var firebaseUser: FirebaseUser? = null

    private val auth = FirebaseAuth.getInstance()

    init {
        val user = auth.currentUser
        if (user == null) {
            runBlocking {
                var result = auth.signInAnonymously().await()
                firebaseUser = result.user
            }
        } else {
            firebaseUser = user
        }

    }

    suspend fun requestToken(): String? {
        checkNotNull(firebaseUser) { "FirebaseUser is null" }

        val token = firebaseUser!!.getIdToken(false).await().token
        if (token == null) {
            Log.e(TAG, "requestToken: No token received!")
        }
        return token
    }

}