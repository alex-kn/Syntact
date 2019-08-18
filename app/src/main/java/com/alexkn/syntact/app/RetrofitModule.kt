package com.alexkn.syntact.app

import com.alexkn.syntact.rest.service.SyntactService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideSyntactService(): SyntactService {

        val gson = GsonBuilder().create()

        return Retrofit.Builder().callbackExecutor(Executors.newSingleThreadExecutor())
                .baseUrl("https://possible-stock-239518.appspot.com/syntact/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(SyntactService::class.java)
    }
}
