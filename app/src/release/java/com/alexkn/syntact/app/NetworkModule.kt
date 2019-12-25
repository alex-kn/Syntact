package com.alexkn.syntact.app

import com.alexkn.syntact.service.SyntactService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideSyntactService(): SyntactService {

        val gson = GsonBuilder().create()

        return Retrofit.Builder().callbackExecutor(Executors.newSingleThreadExecutor())
                .baseUrl("https://syntact-backend-a2jzj3tbsa-ue.a.run.app/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(SyntactService::class.java)
    }
}
