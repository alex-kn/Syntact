package com.alexkn.syntact.presentation.hangman;

import android.app.Application;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.common.LetterColumn;
import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.domain.model.Letter;
import com.alexkn.syntact.domain.model.Phrase;
import com.alexkn.syntact.domain.usecase.ManageLanguages;
import com.alexkn.syntact.domain.usecase.ManageLetters;
import com.alexkn.syntact.domain.usecase.ManagePhrases;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class HangmanViewModel extends AndroidViewModel {

    @Inject
    ManagePhrases managePhrases;

    @Inject
    ManageLanguages manageLanguages;

    @Inject
    ManageLetters manageLetters;

    private Long languagePairId;

    public HangmanViewModel(Application application) {

        super(application);

        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);
    }

    public void setLanguagePairId(Long languagePairId) {

        this.languagePairId = languagePairId;
    }

    boolean solve(Phrase solvablePhrase, Letter letter) {

        boolean successful = managePhrases.makeAttempt(solvablePhrase, letter.getCharacter());
        if (successful) {
            manageLetters.replaceLetter(letter);
        }
        return successful;
    }

    LiveData<List<Phrase>> getSolvablePhrases(Long languagePairId) {

        return managePhrases.getPhrases(languagePairId);
    }

    LiveData<List<Letter>> getLettersLeft() {

        return manageLetters.getLetters(languagePairId, LetterColumn.LEFT);
    }

    LiveData<List<Letter>> getLettersRight() {

        return manageLetters.getLetters(languagePairId, LetterColumn.RIGHT);
    }

    LiveData<LanguagePair> getLanguagePair() {

        return manageLanguages.getLanguagePair(languagePairId);
    }
}
