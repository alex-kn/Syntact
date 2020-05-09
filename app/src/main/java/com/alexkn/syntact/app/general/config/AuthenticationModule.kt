package com.alexkn.syntact.app.general.config

import android.content.Context
import com.alexkn.syntact.app.auth.FirebaseAuthenticationProvider
import com.alexkn.syntact.app.auth.api.AuthenticationProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AuthenticationModule {

    @Singleton
    @Provides
    fun authenticationProvider(context: Context): AuthenticationProvider = FirebaseAuthenticationProvider(context)

}
