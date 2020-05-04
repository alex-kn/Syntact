package com.alexkn.syntact.app.auth.api

interface AuthenticationProvider {
    suspend fun requestToken(): String?
}