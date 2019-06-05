package com.alexkn.syntact.presentation.play.board;

import com.alexkn.syntact.domain.model.Letter;
import com.alexkn.syntact.domain.model.cto.SolvableTranslationCto;

public interface ViewModelCallback {

    boolean handleDrop(SolvableTranslationCto solvableTranslation, Letter letter);
}
