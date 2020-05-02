package com.alexkn.syntact.app.auth

interface AuthenticationProvider {
    suspend fun requestToken(): String?
}