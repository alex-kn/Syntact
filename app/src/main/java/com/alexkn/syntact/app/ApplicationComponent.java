package com.alexkn.syntact.app;

import android.app.Application;

import com.alexkn.syntact.domain.usecase.bucket.CreateBucket;
import com.alexkn.syntact.domain.usecase.bucket.ManageBuckets;
import com.alexkn.syntact.domain.usecase.play.ManageLetters;
import com.alexkn.syntact.domain.usecase.play.ManageSolvableItems;
import com.alexkn.syntact.domain.usecase.play.ManageScore;
import com.alexkn.syntact.domain.util.LetterGenerator;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApplicationModule.class})
@Singleton
public interface ApplicationComponent {

    Application application();

    ManageSolvableItems phraseUseCase();

    CreateBucket createBucket();

    LetterGenerator generateCharactersUseCase();

    ManageBuckets languageManagement();

    ManageLetters letterManagement();

    ManageScore manageScore();

    Property property();
}


