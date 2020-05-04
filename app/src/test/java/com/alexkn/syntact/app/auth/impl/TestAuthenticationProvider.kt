package com.alexkn.syntact.app.auth.impl

import com.alexkn.syntact.app.auth.api.AuthenticationProvider

class TestAuthenticationProvider : AuthenticationProvider {

    override suspend fun requestToken(): String? = "test"

}