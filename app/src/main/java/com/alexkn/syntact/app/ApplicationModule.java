package com.alexkn.syntact.app;

import android.app.Application;

import com.alexkn.syntact.dataaccess.repository.BucketRepositoryImpl;
import com.alexkn.syntact.dataaccess.repository.LetterRepositoryImpl;
import com.alexkn.syntact.dataaccess.repository.PhraseRepositoryImpl;
import com.alexkn.syntact.dataaccess.repository.TemplateRepositoryImpl;
import com.alexkn.syntact.domain.repository.BucketRepository;
import com.alexkn.syntact.domain.repository.LetterRepository;
import com.alexkn.syntact.domain.repository.PhraseRepository;
import com.alexkn.syntact.domain.repository.TemplateRepository;
import com.alexkn.syntact.restservice.SyntactService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
class ApplicationModule {

    private Application syntactApplication;

    public ApplicationModule(SyntactApplication syntactApplication) {

        this.syntactApplication = syntactApplication;
    }

    @Singleton
    @Provides
    SyntactService provideSyntactService() {

        Gson gson = new GsonBuilder().create();

        return new Retrofit.Builder()
                .baseUrl("https://possible-stock-239518.appspot.com/syntact/api/")
                .addConverterFactory(GsonConverterFactory.create(gson)).build()
                .create(SyntactService.class);
    }

    @Provides
    @Singleton
    Application provideApplication() {

        return syntactApplication;
    }

    @Provides
    @Singleton
    PhraseRepository providePhraseRepository(PhraseRepositoryImpl phraseRepository) {

        return phraseRepository;
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
    TemplateRepository provideTemplateRepository(TemplateRepositoryImpl templateRepository) {

        return templateRepository;
    }
}
