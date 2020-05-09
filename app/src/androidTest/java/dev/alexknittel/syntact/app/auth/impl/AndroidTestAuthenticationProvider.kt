package dev.alexknittel.syntact.app.auth.impl

import dev.alexknittel.syntact.app.auth.api.AuthenticationProvider

class AndroidTestAuthenticationProvider : AuthenticationProvider {

    override suspend fun requestToken(): String? = "test"

}