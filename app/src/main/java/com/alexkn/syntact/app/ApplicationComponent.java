package com.alexkn.syntact.app;

import android.app.Application;

import com.alexkn.syntact.domain.usecase.CreateBucket;
import com.alexkn.syntact.domain.usecase.CreateTemplate;
import com.alexkn.syntact.domain.usecase.ManageTemplates;
import com.alexkn.syntact.domain.util.LetterGenerator;
import com.alexkn.syntact.domain.util.PhraseGenerator;
import com.alexkn.syntact.domain.usecase.ManageBuckets;
import com.alexkn.syntact.domain.usecase.ManageLetters;
import com.alexkn.syntact.domain.usecase.ManagePhrases;
import com.alexkn.syntact.domain.usecase.ManageScore;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApplicationModule.class})
@Singleton
public interface ApplicationComponent {

    Application application();

    ManagePhrases phraseUseCase();

    PhraseGenerator generatePhraseUseCase();

    CreateBucket createBucket();

    CreateTemplate createTemplate();

    ManageTemplates manageTemplates();

    LetterGenerator generateCharactersUseCase();

    ManageBuckets languageManagement();

    ManageLetters letterManagement();

    ManageScore manageScore();

    Property property();
}


