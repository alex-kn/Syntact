package com.alexkn.syntact.presentation.hangman.main;

import android.app.Application;

import com.alexkn.syntact.domain.usecase.GenerateCharactersUseCase;
import com.alexkn.syntact.domain.usecase.GeneratePhrasesUseCase;
import com.alexkn.syntact.presentation.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.model.Phrase;
import com.alexkn.syntact.domain.usecase.PhraseUseCase;
import com.alexkn.syntact.presentation.hangman.DaggerHangmanComponent;
import com.alexkn.syntact.presentation.hangman.board.Letter;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class HangmanViewModel extends AndroidViewModel {

    @Inject
    PhraseUseCase phraseUseCase;

    @Inject
    GeneratePhrasesUseCase generatePhrasesUseCase;

    @Inject
    GenerateCharactersUseCase generateCharactersUseCase;

    private MutableLiveData<List<Letter>> lettersLeft = new MutableLiveData<>();

    private MutableLiveData<List<Letter>> lettersRight = new MutableLiveData<>();

    public HangmanViewModel(Application application) {

        super(application);
        DaggerHangmanComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);

        generatePhrasesUseCase.generatePhrasesAsync();

        int initialCharacterCount = 12;
        lettersLeft.setValue(generateCharactersUseCase.generateCharacters(initialCharacterCount).stream().map(Letter::new).collect(
                Collectors.toList()));
        lettersRight.setValue(generateCharactersUseCase.generateCharacters(initialCharacterCount).stream().map(Letter::new).collect(
                Collectors.toList()));
    }

    @SuppressWarnings("ConstantConditions")
    public boolean solve(Phrase solvablePhrase, Letter letter) {

        boolean successful = phraseUseCase.solvePhrase(solvablePhrase, letter.getCharacter());
        if (successful) {
            List<Letter> leftValue = new ArrayList<>(lettersLeft.getValue());
            if (leftValue.remove(letter)) {
                leftValue.add(new Letter(generateCharactersUseCase.generateNewCharacter()));
                lettersLeft.setValue(leftValue);
            }
            List<Letter> rightValue = new ArrayList<>(lettersRight.getValue());
            if (rightValue.remove(letter)) {
                rightValue.add(new Letter(generateCharactersUseCase.generateNewCharacter()));
                lettersRight.setValue(rightValue);
            }
        }
        return successful;
    }

    public LiveData<List<Phrase>> getSolvablePhrases() {

        return phraseUseCase.getPhrases();
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
