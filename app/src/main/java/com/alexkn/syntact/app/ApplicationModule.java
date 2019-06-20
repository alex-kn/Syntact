package com.alexkn.syntact.app;

import com.alexkn.syntact.restservice.SyntactService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
class ApplicationModule {

    @Singleton
    @Provides
    SyntactService provideSyntactService() {

        Gson gson = new GsonBuilder().create();

        return new Retrofit.Builder().callbackExecutor(Executors.newSingleThreadExecutor())
                .baseUrl("https://possible-stock-239518.appspot.com/syntact/api/").addConverterFactory(GsonConverterFactory.create(gson)).build()
                .create(SyntactService.class);
    }
}
