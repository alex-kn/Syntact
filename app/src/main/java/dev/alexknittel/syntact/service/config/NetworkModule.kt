package dev.alexknittel.syntact.service.config

import dagger.Module
import dagger.Provides
import dev.alexknittel.syntact.service.SyntactService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideSyntactService(okHttpClient: OkHttpClient): SyntactService {

        return Retrofit.Builder().callbackExecutor(Executors.newCachedThreadPool())
                .baseUrl("https://www.linguee.com")
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
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
