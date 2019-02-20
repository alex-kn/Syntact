package com.alexkn.syntact.domain.usecase;

import com.alexkn.syntact.domain.repository.LanguagePairRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LanguageScoreManagement {

    @Inject
    LanguagePairRepository languagePairRepository;

    @Inject
    public LanguageScoreManagement() {


    }


}
