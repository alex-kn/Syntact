package dev.alexknittel.syntact.service.config

import dagger.Module
import dagger.Provides
import dev.alexknittel.syntact.service.SyntactService
import io.mockk.mockk
import javax.inject.Singleton

@Module
class TestNetworkModule {

    @Singleton
    @Provides
    fun provideSyntactService(): SyntactService = mockk()

}
