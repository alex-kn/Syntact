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
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.inject.Inject;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.recyclerview.widget.RecyclerView;

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

    private LiveData<LanguagePair> languagePair;

    private LiveData<List<Phrase>> phrases;

    private LiveData<List<Letter>> lettersLeft;

    private LiveData<List<Letter>> lettersRight;

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

        boolean successful = managePhrases.isLetterCorrect(solvablePhrase, letter.getCharacter());
        AsyncTask.execute(() -> managePhrases.makeAttempt(solvablePhrase, letter.getCharacter()));
        if (successful) {
            AsyncTask.execute(() -> manageLetters.replaceLetter(letter));
        }
        return successful;
    }

    public void reloadLetters() {

        manageLetters.reloadLetters(languagePairId);
    }

    public void initLanguage(Long languagePairId) {

        this.languagePairId = languagePairId;
        languagePair = manageLanguages.getLanguagePair(languagePairId);
        phrases = managePhrases.getPhrases(languagePairId);
        lettersLeft = manageLetters.getLetters(languagePairId, LetterColumn.LEFT);
        lettersRight = manageLetters.getLetters(languagePairId, LetterColumn.RIGHT);
    }

    public int calculateMaxScoreForLevel(int level) {

        return manageScore.calculateMaxForLevel(level);
    }

    LiveData<List<Phrase>> getSolvablePhrases() {

        return phrases;
    }

    LiveData<List<Letter>> getLettersLeft() {

        return lettersLeft;
    }

    LiveData<List<Letter>> getLettersRight() {

        return lettersRight;
    }

    LiveData<LanguagePair> getLanguagePair() {

        return languagePair;
    }
}
