package dev.alexknittel.syntact.app.auth

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dev.alexknittel.syntact.app.TAG
import dev.alexknittel.syntact.app.auth.api.AuthenticationProvider
import kotlinx.coroutines.tasks.await

class FirebaseAuthenticationProvider constructor(context: Context) : AuthenticationProvider {

    private var firebaseUser: FirebaseUser? = null

    private val auth = FirebaseAuth.getInstance()

    init {
        FirebaseApp.initializeApp(context)
        val user = auth.currentUser
        if (user == null) {
            auth.signInAnonymously().addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "signInAnonymously:sucess")
                    firebaseUser = auth.currentUser
                } else {
                    Log.e(TAG, "signInAnonymously:failed", it.exception)
                }
            }
        } else {
            firebaseUser = user
        }

    }

    override suspend fun requestToken(): String? {
        checkNotNull(firebaseUser) { "FirebaseUser is null" }

        val token = firebaseUser!!.getIdToken(false).await().token
        if (token == null) {
            Log.e(TAG, "requestToken: No token received!")
        }
        return token
    }
}