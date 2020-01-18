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

//    @Singleton
//    @Provides
//    fun provideSyntactService(): SyntactService {
//        val gson = GsonBuilder().setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
//
//        return Retrofit.Builder().callbackExecutor(Executors.newSingleThreadExecutor())
//                .baseUrl("https://syntact-backend-wsrzejkcua-uc.a.run.app/")
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build()
//                .create(SyntactService::class.java)
//    }
}
