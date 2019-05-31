package com.alexkn.syntact.app;

import android.app.Application;

import com.alexkn.syntact.domain.usecase.bucket.CreateBucket;
import com.alexkn.syntact.domain.usecase.template.CreateTemplate;
import com.alexkn.syntact.domain.usecase.template.ManageTemplates;
import com.alexkn.syntact.domain.util.LetterGenerator;
import com.alexkn.syntact.domain.util.PhraseGenerator;
import com.alexkn.syntact.domain.usecase.bucket.ManageBuckets;
import com.alexkn.syntact.domain.usecase.play.ManageLetters;
import com.alexkn.syntact.domain.usecase.play.ManagePhrases;
import com.alexkn.syntact.domain.usecase.play.ManageScore;

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


