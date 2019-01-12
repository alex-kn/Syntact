package com.alexkn.syntact.domain;

import android.app.Application;

import com.alexkn.syntact.domain.usecase.PhraseUseCase;
import com.alexkn.syntact.presentation.mainmenu.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApplicationModule.class})
@Singleton
public interface SyntactComponent {

    Application application();
    PhraseUseCase phraseUseCase();

}


