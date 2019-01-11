package com.alexkn.syntact.presentation.hangman.board;

import com.alexkn.syntact.domain.model.Phrase;

public interface ViewModelCallback {

    boolean handleDrop(Phrase solvablePhrase, Letter letter);
}
