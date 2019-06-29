package com.alexkn.syntact.app

import com.alexkn.syntact.restservice.SyntactService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
internal class RetrofitModule {

    @Singleton
    @Provides
    fun provideSyntactService(): SyntactService {

        val gson = GsonBuilder().create()

        return Retrofit.Builder().callbackExecutor(Executors.newSingleThreadExecutor())
                .baseUrl("https://possible-stock-239518.appspot.com/syntact/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(SyntactService::class.java)
    }
}
