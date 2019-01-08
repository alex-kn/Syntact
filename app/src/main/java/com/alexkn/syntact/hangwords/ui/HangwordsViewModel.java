package com.alexkn.syntact.hangwords.ui;

import android.app.Application;

import com.alexkn.syntact.hangwords.logic.PhraseManagement;
import com.alexkn.syntact.hangwords.logic.api.to.SolvablePhrase;
import com.alexkn.syntact.hangwords.ui.util.Letter;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class HangwordsViewModel extends AndroidViewModel {

    private PhraseManagement phrasesManagement;

    private MutableLiveData<List<Letter>> lettersLeft = new MutableLiveData<>();

    private MutableLiveData<List<Letter>> lettersRight = new MutableLiveData<>();

    public HangwordsViewModel(Application application) {

        super(application);
        lettersLeft.setValue(LetterGenerator.generateLetters(7));
        lettersRight.setValue(LetterGenerator.generateLetters(7));
        phrasesManagement = new PhraseManagement(application);
    }

    @SuppressWarnings("ConstantConditions")
    boolean solve(SolvablePhrase solvablePhrase, Letter letter) {

        boolean successful = phrasesManagement.solvePhrase(solvablePhrase, letter);
        if (successful) {
            List<Letter> leftValue = new ArrayList<>(lettersLeft.getValue());
            leftValue.remove(letter);
            lettersLeft.setValue(leftValue);
            List<Letter> rightValue = new ArrayList<>(lettersRight.getValue());
            rightValue.remove(letter);
            lettersRight.setValue(rightValue);
        }
        return successful;
    }

    LiveData<List<SolvablePhrase>> getSolvablePhrases() {

        return phrasesManagement.getSolvablePhrasesLiveData();
    }

    public LiveData<List<Letter>> getLettersLeft() {

        return lettersLeft;
    }

    public LiveData<List<Letter>> getLettersRight() {

        return lettersRight;
    }

    private static class LetterGenerator {

        @NonNull
        static List<Letter> generateLetters(int length) {

            List<Letter> letters = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                letters.add(generateLetter());
            }
            return letters;
        }

        static Letter generateLetter() {

            return new Letter(RandomStringUtils.randomAlphabetic(1).toUpperCase().charAt(0));
        }
    }
}
