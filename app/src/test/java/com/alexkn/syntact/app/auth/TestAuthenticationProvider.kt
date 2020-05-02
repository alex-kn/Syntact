package com.alexkn.syntact.app.auth

class TestAuthenticationProvider : AuthenticationProvider {

    override suspend fun requestToken(): String? = "test"

}