package com.alexkn.syntact.domain.usecase;

import androidx.lifecycle.LiveData;

import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.domain.repository.LanguagePairRepository;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

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

        if (languagePairRepository.languagePairExists(languageLeft, languageRight)) {
            return;
        }

        LanguagePair activeLanguagePair = new LanguagePair();
        activeLanguagePair.setLanguageLeft(languageLeft);
        activeLanguagePair.setLanguageRight(languageRight);
        activeLanguagePair.setScore(0);
        activeLanguagePair.setLevel(0);
        activeLanguagePair.setStreak(0);
        Long insertedLanguageId = languagePairRepository.insert(activeLanguagePair);
        manageLetters.generateLetters(insertedLanguageId);
        managePhrases.initializePhrases(insertedLanguageId, languageLeft, languageRight);
    }

    public void removeLanguage(Long id) {

        languagePairRepository.delete(id);
    }

    public LiveData<LanguagePair> getLanguagePair(Long id) {

        return languagePairRepository.findLanguagePair(id);
    }

    public LiveData<List<LanguagePair>> getLanguagePairs() {

        return languagePairRepository.findAllLanguagePairs();
    }
}
