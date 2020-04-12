package com.alexkn.syntact.app

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthProv @Inject constructor() {

    fun requestToken(): String? {
        Log.i(TAG, "requestToken: Returning mocked token")
        return "syntact-access"
    }

//    private var firebaseUser: FirebaseUser? = null
//
//    private val auth = FirebaseAuth.getInstance()
//
//    init {
//        val user = auth.currentUser
//        if (user == null) {
//            auth.signInAnonymously().addOnCompleteListener {
//                if (it.isSuccessful) {
//                    Log.d(TAG, "signInAnonymously:sucess")
//                    firebaseUser = auth.currentUser
//                } else {
//                    Log.e(TAG, "signInAnonymously:failed", it.exception)
//                }
//            }
//        } else {
//            firebaseUser = user
//        }
//
//    }
//
//    suspend fun requestToken(): String? {
//        checkNotNull(firebaseUser) { "FirebaseUser is null" }
//
//        val token = firebaseUser!!.getIdToken(false).await().token
//        if (token == null) {
//            Log.e(TAG, "requestToken: No token received!")
//        }
//        return token
//    }
}