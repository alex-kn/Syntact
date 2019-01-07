package com.alexkn.syntact.hangwords.ui;

import android.app.Application;

import com.alexkn.syntact.hangwords.logic.LetterManagement;
import com.alexkn.syntact.hangwords.logic.PhraseManagement;
import com.alexkn.syntact.hangwords.logic.api.to.SolvablePhrase;
import com.alexkn.syntact.hangwords.ui.util.Letter;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class HangwordsViewModel extends AndroidViewModel {

    private PhraseManagement phrasesManagement;

    private LetterManagement letterManagement;

    public HangwordsViewModel(Application application) {

        super(application);
        phrasesManagement = new PhraseManagement(application);
        letterManagement = new LetterManagement(3);
    }

    boolean solve(SolvablePhrase solvablePhrase, Integer letterId) {

        Letter letter = letterManagement.findLetter(letterId);
        boolean successful = phrasesManagement.solvePhrase(solvablePhrase, letter);
        if (successful) letterManagement.removeLetter(letter);

        return successful;
    }

    LiveData<List<Letter>> getLetters(int bucket) {

        return letterManagement.getLetters(bucket);
    }

    LiveData<List<SolvablePhrase>> getSolvablePhrases() {

        return phrasesManagement.getSolvablePhrasesLiveData();
    }
}
