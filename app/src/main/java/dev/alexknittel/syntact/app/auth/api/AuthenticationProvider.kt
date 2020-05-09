package dev.alexknittel.syntact.app.auth.api

interface AuthenticationProvider {
    suspend fun requestToken(): String?
}