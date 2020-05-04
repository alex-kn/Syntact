package com.alexkn.syntact.app.auth.config

import com.alexkn.syntact.app.auth.api.AuthenticationProvider
import com.alexkn.syntact.app.auth.impl.TestAuthenticationProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestAuthenticationModule {

    @Singleton
    @Provides
    fun authenticationProvider(): AuthenticationProvider = TestAuthenticationProvider()

}
