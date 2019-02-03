package com.alexkn.syntact.domain.usecase;

import android.app.Application;
import android.os.AsyncTask;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.Phrase;
import com.alexkn.syntact.domain.repository.PhraseRepository;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Locale;

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

    private void generatePhrases() {

        if (phraseRepository.count() > 5) return;
        ArrayList<Phrase> phrases = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            Phrase phrase = new Phrase();
            String randomString = RandomStringUtils.randomAlphabetic(4);
            phrase.setClue(randomString);
            phrase.setSolution(randomString);
            phrase.setAttempt(StringUtils
                    .repeat(application.getString(R.string.empty), phrase.getSolution().length()));
            phrase.setClueLocale(Locale.ENGLISH);
            phrase.setSolutionLocale(Locale.GERMAN);
            phraseRepository.insert(phrase);
            phrases.add(phrase);
        }
        phraseRepository.insert(phrases);
    }

    public void generatePhrasesAsync() {

        AsyncTask.execute(this::generatePhrases);
    }
}
