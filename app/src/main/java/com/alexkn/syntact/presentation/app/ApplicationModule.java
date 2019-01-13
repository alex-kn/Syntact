package com.alexkn.syntact.presentation.app;

import android.app.Application;

import com.alexkn.syntact.dataaccess.phrase.PhraseRepositoryImpl;
import com.alexkn.syntact.domain.repository.PhraseRepository;
import com.alexkn.syntact.presentation.app.SyntactApplication;

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
