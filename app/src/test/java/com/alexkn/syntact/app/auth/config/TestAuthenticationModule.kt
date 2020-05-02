package com.alexkn.syntact.app.auth.config

import com.alexkn.syntact.app.auth.AuthenticationProvider
import com.alexkn.syntact.app.auth.TestAuthenticationProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestAuthenticationModule {

    @Singleton
    @Provides
    fun authenticationProvider(): AuthenticationProvider = TestAuthenticationProvider()

}
