package com.alexkn.syntact.presentation.hangman;

import com.alexkn.syntact.domain.model.Letter;
import com.alexkn.syntact.domain.model.SolvableItem;

public interface ViewModelCallback {

    boolean handleDrop(SolvableItem solvableSolvableItem, Letter letter);
}
