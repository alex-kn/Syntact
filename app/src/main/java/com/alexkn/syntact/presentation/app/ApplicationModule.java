package com.alexkn.syntact.presentation.app;

import android.app.Application;

import com.alexkn.syntact.dataaccess.language.LanguagePairRepositoryImpl;
import com.alexkn.syntact.dataaccess.phrase.PhraseRepositoryImpl;
import com.alexkn.syntact.domain.repository.LanguagePairRepository;
import com.alexkn.syntact.domain.repository.PhraseRepository;

import org.apache.commons.lang3.tuple.Pair;

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
    LanguagePairRepository provideActiveLanguagePairRepository(
            LanguagePairRepositoryImpl activeLanguagePairRepository) {

        return activeLanguagePairRepository;
    }
}
