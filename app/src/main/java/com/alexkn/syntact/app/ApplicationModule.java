package com.alexkn.syntact.app;

import android.app.Application;

import com.alexkn.syntact.dataaccess.repository.AttemptRepositoryImpl;
import com.alexkn.syntact.dataaccess.repository.BucketRepositoryImpl;
import com.alexkn.syntact.dataaccess.repository.ClueRepositoryImpl;
import com.alexkn.syntact.dataaccess.repository.LetterRepositoryImpl;
import com.alexkn.syntact.dataaccess.repository.SolvableItemRepositoryImpl;
import com.alexkn.syntact.domain.repository.AttemptRepository;
import com.alexkn.syntact.domain.repository.BucketRepository;
import com.alexkn.syntact.domain.repository.ClueRepository;
import com.alexkn.syntact.domain.repository.LetterRepository;
import com.alexkn.syntact.domain.repository.SolvableItemRepository;
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
                .baseUrl("https://possible-stock-239518.appspot.com/syntact/api/")
                .addConverterFactory(GsonConverterFactory.create(gson)).build()
                .create(SyntactService.class);
    }

    @Provides
    @Singleton
    SolvableItemRepository provideSolvableItemRepository(
            SolvableItemRepositoryImpl solvableItemRepository) {

        return solvableItemRepository;
    }

    @Provides
    @Singleton
    BucketRepository provideActiveBucketRepository(BucketRepositoryImpl bucketRepository) {

        return bucketRepository;
    }

    @Provides
    @Singleton
    LetterRepository provideLetterRepository(LetterRepositoryImpl letterRepository) {

        return letterRepository;
    }

    @Provides
    @Singleton
    ClueRepository provideClueRepository(ClueRepositoryImpl clueRepository) {

        return clueRepository;
    }

    @Provides
    @Singleton
    AttemptRepository provideAttemptRepository(AttemptRepositoryImpl attemptRepository) {

        return attemptRepository;
    }
}
