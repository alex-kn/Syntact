package com.alexkn.syntact.domain.usecase;

import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.domain.repository.LanguagePairRepository;
import com.alexkn.syntact.domain.service.PhraseGenerator;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;

@Singleton
public class ManageLanguages {

    @Inject
    LanguagePairRepository languagePairRepository;

    @Inject
    ManageLetters manageLetters;

    @Inject
    ManagePhrases managePhrases;

    @Inject
    ManageLanguages() {

    }

    public void addLanguage(Locale languageLeft, Locale languageRight) {

        LanguagePair activeLanguagePair = new LanguagePair();
        activeLanguagePair.setLanguageLeft(languageLeft);
        activeLanguagePair.setLanguageRight(languageRight);
        Long insertedLanguageId = languagePairRepository.insert(activeLanguagePair);
        manageLetters.generateLetters(insertedLanguageId);
        managePhrases.generateGermanEnglishPhrases(insertedLanguageId, languageLeft, languageRight);
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
