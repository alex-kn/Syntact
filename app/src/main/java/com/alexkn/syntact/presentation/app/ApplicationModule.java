package com.alexkn.syntact.presentation.app;

import android.app.Application;

import com.alexkn.syntact.dataaccess.phrase.impl.PhraseRepositoryImpl;
import com.alexkn.syntact.domain.repository.PhraseRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class ApplicationModule {

    private Application syntactApplication;

    public ApplicationModule(SyntactApplication syntactApplication) {

        this.syntactApplication = syntactApplication;
    }

    @Provides
    @Singleton
    Application provideApplication(){
        return syntactApplication;
    }

    @Provides
    @Singleton
    PhraseRepository providePhraseRepository(PhraseRepositoryImpl phraseRepository) {
        return phraseRepository;
    }
}
