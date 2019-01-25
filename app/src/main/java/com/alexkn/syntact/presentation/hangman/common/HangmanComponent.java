package com.alexkn.syntact.presentation.hangman.common;

import com.alexkn.syntact.presentation.app.ApplicationComponent;
import com.alexkn.syntact.presentation.hangman.common.HangmanViewModel;
import com.alexkn.syntact.presentation.scope.ActivityScope;

import dagger.Component;

@Component(dependencies = ApplicationComponent.class)
@ActivityScope
public interface HangmanComponent {

    void inject(HangmanViewModel hangmanBoardViewModel);
}
