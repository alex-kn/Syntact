package com.alexkn.syntact.presentation.view;

import com.alexkn.syntact.presentation.app.ApplicationComponent;
import com.alexkn.syntact.presentation.hangman.common.HangmanViewModel;
import com.alexkn.syntact.presentation.menu.LanguagesViewModel;
import com.alexkn.syntact.presentation.scope.ActivityScope;

import dagger.Component;

@Component(dependencies = ApplicationComponent.class)
@ActivityScope
public interface ViewComponent {

    void inject(HangmanViewModel hangmanBoardViewModel);

    void inject(LanguagesViewModel languagesViewModel);
}
