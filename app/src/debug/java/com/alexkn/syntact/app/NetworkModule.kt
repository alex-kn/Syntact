package com.alexkn.syntact.app

import com.alexkn.syntact.service.SyntactService
import com.alexkn.syntact.service.SyntactServiceMock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideSyntactService(): SyntactService = SyntactServiceMock()
}