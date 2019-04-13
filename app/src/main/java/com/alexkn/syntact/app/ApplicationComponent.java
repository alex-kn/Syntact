package com.alexkn.syntact.app;

import android.app.Application;

import com.alexkn.syntact.domain.service.LetterGenerator;
import com.alexkn.syntact.domain.service.PhraseGenerator;
import com.alexkn.syntact.domain.usecase.ManageLanguages;
import com.alexkn.syntact.domain.usecase.ManageLetters;
import com.alexkn.syntact.domain.usecase.ManagePhrases;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApplicationModule.class})
@Singleton
public interface ApplicationComponent {

    Application application();

    ManagePhrases phraseUseCase();

    PhraseGenerator generatePhraseUseCase();

    LetterGenerator generateCharactersUseCase();

    ManageLanguages languageManagement();

    ManageLetters letterManagement();
}


