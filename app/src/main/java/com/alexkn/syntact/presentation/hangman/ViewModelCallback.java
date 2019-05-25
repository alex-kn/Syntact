package com.alexkn.syntact.presentation.hangman;

import com.alexkn.syntact.domain.model.Letter;
import com.alexkn.syntact.domain.model.SolvableItem;
import com.alexkn.syntact.domain.model.SolvableTranslation;

public interface ViewModelCallback {

    boolean handleDrop(SolvableTranslation solvableTranslation, Letter letter);
}
