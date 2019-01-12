package com.alexkn.syntact.presentation.hangman;

import com.alexkn.syntact.domain.SyntactComponent;
import com.alexkn.syntact.presentation.hangman.board.HangmanBoardViewModel;
import com.alexkn.syntact.presentation.common.ActivityScope;

import dagger.Component;

@Component(dependencies = SyntactComponent.class)
@ActivityScope
public interface HangmanComponent {

    void inject(HangmanBoardViewModel hangmanBoardViewModel);
}
