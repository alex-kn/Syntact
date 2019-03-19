package com.alexkn.syntact.domain.usecase;

import android.app.Application;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.domain.model.Phrase;
import com.alexkn.syntact.domain.repository.PhraseRepository;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GeneratePhrasesUseCase {

    @Inject
    PhraseRepository phraseRepository;

    @Inject
    Application application;

    @Inject
    GeneratePhrasesUseCase() {}

    public void generatePhrases(LanguagePair languagePair) {

//        ArrayList<Phrase> phrases = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//
//            Phrase phrase = new Phrase();
//            phrase.setLanguagePairId(languagePair.getId());
//            String randomString = RandomStringUtils.randomAlphabetic(4);
//            phrase.setClue(randomString);
//            phrase.setSolution(randomString);
//            phrase.setAttempt(StringUtils
//                    .repeat(application.getString(R.string.empty), phrase.getSolution().length()));
//
//            phrase.setConsecutiveCorrectAnswers(0);
//            phrase.setEasiness(2.5f);
//            phrase.setNextDueDate(Instant.now());
//            phrase.setClueLocale(languagePair.getLanguageLeft());
//            phrase.setSolutionLocale(languagePair.getLanguageRight());
//            phraseRepository.insert(phrase);
//            phrases.add(phrase);
//        }
//        phraseRepository.insert(phrases);
    }


}
