package com.alexkn.syntact.presentation.hangman.main;

import android.app.Application;

import com.alexkn.syntact.presentation.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.model.Phrase;
import com.alexkn.syntact.domain.usecase.PhraseUseCase;
import com.alexkn.syntact.presentation.hangman.DaggerHangmanComponent;
import com.alexkn.syntact.presentation.hangman.board.Letter;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class HangmanViewModel extends AndroidViewModel {

    @Inject
    PhraseUseCase phrasesManagement;

    private MutableLiveData<List<Letter>> lettersLeft = new MutableLiveData<>();

    private MutableLiveData<List<Letter>> lettersRight = new MutableLiveData<>();

    public HangmanViewModel(Application application) {

        super(application);

        DaggerHangmanComponent.builder()
                .applicationComponent(((ApplicationComponentProvider) getApplication()).getApplicationComponent())
                .build().inject(this);
        lettersLeft.setValue(LetterGenerator.generateLetters(7));
        lettersRight.setValue(LetterGenerator.generateLetters(7));
    }

    @SuppressWarnings("ConstantConditions")
    public boolean solve(Phrase solvablePhrase, Letter letter) {

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

    public LiveData<List<Phrase>> getSolvablePhrases() {

        return phrasesManagement.getPhrases();
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
