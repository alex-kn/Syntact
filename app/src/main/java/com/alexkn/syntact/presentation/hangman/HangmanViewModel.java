package com.alexkn.syntact.presentation.hangman;

import android.app.Application;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.common.LetterColumn;
import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.domain.model.Letter;
import com.alexkn.syntact.domain.model.Phrase;
import com.alexkn.syntact.domain.usecase.GenerateCharactersUseCase;
import com.alexkn.syntact.domain.usecase.GeneratePhrasesUseCase;
import com.alexkn.syntact.domain.usecase.LanguageManagement;
import com.alexkn.syntact.domain.usecase.LetterManagement;
import com.alexkn.syntact.domain.usecase.PhraseUseCase;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class HangmanViewModel extends AndroidViewModel {

    @Inject
    public PhraseUseCase phraseUseCase;

    @Inject
    public GeneratePhrasesUseCase generatePhrasesUseCase;

    @Inject
    public GenerateCharactersUseCase generateCharactersUseCase;

    @Inject
    public LanguageManagement languageManagement;

    @Inject
    public LetterManagement letterManagement;

    private LiveData<LanguagePair> languagePair;

    private Long languagePairId;

    private int initialCharacterCount = 12;

    public HangmanViewModel(Application application) {

        super(application);

        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);
    }

    public void setLanguagePairId(Long languagePairId) {

        this.languagePairId = languagePairId;
    }

    public void loadPhrases(LanguagePair languagePair) {

        //TODO do for new language
        generatePhrasesUseCase.generatePhrases(languagePair);
    }

    public boolean solve(Phrase solvablePhrase, Letter letter) {

        boolean successful = phraseUseCase.solvePhrase(solvablePhrase, letter.getCharacter());
        if (successful) {
            letterManagement.replaceLetter(letter);
        }
        return successful;
    }

    public LiveData<List<Phrase>> getSolvablePhrases(Long languagePairId) {

        return phraseUseCase.getPhrases(languagePairId);
    }

    public LiveData<List<Letter>> getLettersLeft() {

        return letterManagement.getLetters(LetterColumn.LEFT);
    }

    public LiveData<List<Letter>> getLettersRight() {

        return letterManagement.getLetters(LetterColumn.RIGHT);
    }

    public LiveData<LanguagePair> getLanguagePair() {

        return languageManagement.getLanguagePair(languagePairId);
    }
}
