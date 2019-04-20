package com.alexkn.syntact.presentation.hangman;

import android.app.Application;
import android.os.AsyncTask;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.common.LetterColumn;
import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.domain.model.Letter;
import com.alexkn.syntact.domain.model.Phrase;
import com.alexkn.syntact.domain.usecase.ManageLanguages;
import com.alexkn.syntact.domain.usecase.ManageLetters;
import com.alexkn.syntact.domain.usecase.ManagePhrases;
import com.alexkn.syntact.domain.usecase.ManageScore;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class HangmanViewModel extends AndroidViewModel {

    @Inject
    ManagePhrases managePhrases;

    @Inject
    ManageLanguages manageLanguages;

    @Inject
    ManageLetters manageLetters;

    @Inject
    ManageScore manageScore;

    private MediatorLiveData<Integer> maxScore = new MediatorLiveData<>();

    private Long languagePairId;

    public HangmanViewModel(Application application) {

        super(application);

        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);

//        maxScore.addSource(getLanguagePair(), languagePair -> maxScore
//                .setValue(manageScore.calculateMaxForLevel(languagePair.getLevel() + 1)));
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

    public void reloadLetters() {

        AsyncTask.execute(() -> manageLetters.reloadLetters(languagePairId));
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

    LiveData<Integer> getMaxScore() {

        return maxScore;
    }
}
