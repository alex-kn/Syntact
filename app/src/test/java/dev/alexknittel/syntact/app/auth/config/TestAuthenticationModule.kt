package dev.alexknittel.syntact.app.auth.config

import dagger.Module
import dagger.Provides
import dev.alexknittel.syntact.app.auth.api.AuthenticationProvider
import dev.alexknittel.syntact.app.auth.impl.TestAuthenticationProvider
import javax.inject.Singleton

@Module
class TestAuthenticationModule {

    @Singleton
    @Provides
    fun authenticationProvider(): AuthenticationProvider = TestAuthenticationProvider()

}
