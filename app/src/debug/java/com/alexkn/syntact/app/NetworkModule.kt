package com.alexkn.syntact.app

import com.alexkn.syntact.service.SyntactService
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

//    @Singleton
//    @Provides
//    fun provideSyntactService(): SyntactService = SyntactServiceMock()

    @Singleton
    @Provides
    fun provideSyntactService(okHttpClient: OkHttpClient): SyntactService {

        val gson = GsonBuilder().setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

        return Retrofit.Builder().callbackExecutor(Executors.newCachedThreadPool())
                .baseUrl("https://syntact-backend-wsrzejkcua-uc.a.run.app/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(SyntactService::class.java)
    }

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {

        return with(OkHttpClient.Builder()) {
            connectTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            build()
        }
    }
}
