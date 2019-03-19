package com.alexkn.syntact.app;

import android.app.Application;

import com.alexkn.syntact.domain.usecase.LanguageManagement;
import com.alexkn.syntact.domain.usecase.GenerateCharactersUseCase;
import com.alexkn.syntact.domain.usecase.GeneratePhrasesUseCase;
import com.alexkn.syntact.domain.usecase.LanguageScoreManagement;
import com.alexkn.syntact.domain.usecase.PhraseUseCase;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApplicationModule.class})
@Singleton
public interface ApplicationComponent {

    Application application();

    PhraseUseCase phraseUseCase();

    GeneratePhrasesUseCase generatePhraseUseCase();

    GenerateCharactersUseCase generateCharactersUseCase();

    LanguageManagement languageManagement();

    LanguageScoreManagement languageScoreManagement();
}

