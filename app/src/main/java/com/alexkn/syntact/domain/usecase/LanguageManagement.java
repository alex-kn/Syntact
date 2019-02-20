package com.alexkn.syntact.domain.usecase;

import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.domain.repository.LanguagePairRepository;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;

@Singleton
public class LanguageManagement {

    @Inject
    LanguagePairRepository languagePairRepository;

    @Inject
    public LanguageManagement() {

    }

    public void addLanguage(Locale language) {

    }

    public void removeLanguage(Locale language) {

    }

    public LiveData<List<LanguagePair>> getLanguagePairs() {

        return languagePairRepository.findAllLanguagePairs();
    }
}
