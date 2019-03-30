package com.alexkn.syntact.app;

import android.app.Application;

import com.alexkn.syntact.dataaccess.language.LanguagePairRepositoryImpl;
import com.alexkn.syntact.dataaccess.letter.LetterRepositoryImpl;
import com.alexkn.syntact.dataaccess.phrase.PhraseRepositoryImpl;
import com.alexkn.syntact.domain.repository.LanguagePairRepository;
import com.alexkn.syntact.domain.repository.LetterRepository;
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
            LanguagePairRepositoryImpl languagePairRepository) {

        return languagePairRepository;
    }

    @Provides
    @Singleton
    LetterRepository provideLetterRepository(LetterRepositoryImpl letterRepository) {
        return letterRepository;
    }
}
