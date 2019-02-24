package com.alexkn.syntact.presentation.hangman;

import android.app.Application;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.domain.model.Phrase;
import com.alexkn.syntact.domain.usecase.GenerateCharactersUseCase;
import com.alexkn.syntact.domain.usecase.GeneratePhrasesUseCase;
import com.alexkn.syntact.domain.usecase.LanguageManagement;
import com.alexkn.syntact.domain.usecase.PhraseUseCase;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;

import org.intellij.lang.annotations.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.inject.Inject;

import androidx.core.os.ConfigurationCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class HangmanViewModel extends AndroidViewModel {

    @Inject
    public PhraseUseCase phraseUseCase;

    @Inject
    public GeneratePhrasesUseCase generatePhrasesUseCase;

    @Inject
    public GenerateCharactersUseCase generateCharactersUseCase;

    @Inject
    public LanguageManagement languageManagement;

    private LiveData<LanguagePair> languagePair;

    private MutableLiveData<List<Letter>> lettersLeft = new MutableLiveData<>();

    private MutableLiveData<List<Letter>> lettersRight = new MutableLiveData<>();

    private int languagePairId;

    public HangmanViewModel(Application application) {

        super(application);
        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);
        generatePhrasesUseCase.generatePhrasesAsync();
        loadLetters();
    }

    public void setLanguagePairId(int languagePairId) {

        this.languagePairId = languagePairId;
    }

    private void loadLetters() {

        int initialCharacterCount = 12;

        AsyncTask.execute(() -> {
            lettersLeft.postValue(
                    generateCharactersUseCase.generateCharacters(initialCharacterCount).stream()
                            .map(Letter::new).collect(Collectors.toList()));
            lettersRight.postValue(
                    generateCharactersUseCase.generateCharacters(initialCharacterCount).stream()
                            .map(Letter::new).collect(Collectors.toList()));
        });
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

        return phraseUseCase.getPhrases(
                ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0));
    }

    public LiveData<List<Letter>> getLettersLeft() {

        return lettersLeft;
    }

    public LiveData<List<Letter>> getLettersRight() {

        return lettersRight;
    }

    public LiveData<LanguagePair> getLanguagePair(){

        return languageManagement.getLanguagePair(languagePairId);
    }
}
