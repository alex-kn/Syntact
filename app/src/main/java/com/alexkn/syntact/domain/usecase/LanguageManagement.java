package com.alexkn.syntact.domain.usecase;

import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.domain.repository.LanguagePairRepository;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;

@Singleton
public class LanguageManagement {//TODO separate into interfaces

    @Inject
    LanguagePairRepository languagePairRepository;

    @Inject
    LetterManagement letterManagement;

    @Inject
    GeneratePhrasesUseCase generatePhrasesUseCase;

    @Inject
    public LanguageManagement() {

    }

    public void addLanguage(Locale languageLeft, Locale languageRight) {

        LanguagePair activeLanguagePair = new LanguagePair();
        activeLanguagePair.setLanguageLeft(languageLeft);
        activeLanguagePair.setLanguageRight(languageRight);
        Long insertedLanguageId = languagePairRepository.insert(activeLanguagePair);
        letterManagement.generateLetters(insertedLanguageId);
        generatePhrasesUseCase.generatePhrases(insertedLanguageId);
    }

    public void removeLanguage(Locale language) {

    }

    public LiveData<LanguagePair> getLanguagePair(Long id) {

        return languagePairRepository.findLanguagePair(id);
    }

    public LiveData<List<LanguagePair>> getLanguagePairs() {

        return languagePairRepository.findAllLanguagePairs();
    }
}
