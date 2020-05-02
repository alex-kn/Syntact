package com.alexkn.syntact.service.config

import com.alexkn.syntact.service.SyntactService
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Singleton

@Module
class TestNetworkModule {

    @Singleton
    @Provides
    fun provideSyntactService(): SyntactService = mockk()

}
