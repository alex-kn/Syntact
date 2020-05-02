package com.alexkn.syntact.app.general.config

import android.content.Context
import com.alexkn.syntact.app.auth.AuthenticationProvider
import com.alexkn.syntact.app.auth.FirebaseAuthenticationProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AuthenticationModule {

    @Singleton
    @Provides
    fun authenticationProvider(context: Context): AuthenticationProvider = FirebaseAuthenticationProvider(context)

}
