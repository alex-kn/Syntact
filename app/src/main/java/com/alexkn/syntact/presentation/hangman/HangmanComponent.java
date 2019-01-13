package com.alexkn.syntact.presentation.hangman;

import com.alexkn.syntact.presentation.app.ApplicationComponent;
import com.alexkn.syntact.presentation.hangman.main.HangmanViewModel;
import com.alexkn.syntact.presentation.scope.ActivityScope;

import dagger.Component;

@Component(dependencies = ApplicationComponent.class)
@ActivityScope
public interface HangmanComponent {

    void inject(HangmanViewModel hangmanBoardViewModel);
}
