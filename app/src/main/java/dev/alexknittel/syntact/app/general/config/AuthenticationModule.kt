package dev.alexknittel.syntact.app.general.config

import android.content.Context
import dagger.Module
import dagger.Provides
import dev.alexknittel.syntact.app.auth.FirebaseAuthenticationProvider
import dev.alexknittel.syntact.app.auth.api.AuthenticationProvider
import javax.inject.Singleton

@Module
class AuthenticationModule {

    @Singleton
    @Provides
    fun authenticationProvider(context: Context): AuthenticationProvider = FirebaseAuthenticationProvider(context)

}
