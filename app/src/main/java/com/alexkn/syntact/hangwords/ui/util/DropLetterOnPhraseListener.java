package com.alexkn.syntact.hangwords.ui.util;

import com.alexkn.syntact.hangwords.logic.SolvablePhrase;

public interface DropLetterOnPhraseListener {

    boolean handleDrop(SolvablePhrase solvablePhrase, Integer letterId);
}
