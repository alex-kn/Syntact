package com.alexkn.syntact.domain.usecase;

import android.app.Application;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.Phrase;
import com.alexkn.syntact.domain.repository.PhraseRepository;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;

@Singleton
public class PhraseUseCase {

    @Inject
    PhraseRepository phraseRepository;

    @Inject
    Application application;

    @Inject
    LanguageScoreManagement languageScoreManagement;

    private LiveData<List<Phrase>> phrases;

    @Inject
    PhraseUseCase() { }

    public boolean solvePhrase(Phrase phrase, Character character) {

        if (!isLetterCorrect(phrase, character)) {
            return false;
        }
        String attempt = updateCurrentText(phrase, character);
        if (!attempt.contains(application.getString(R.string.empty))) {
            phraseRepository.updateLastSolved(phrase.getId(), Instant.now());
//            languageScoreManagement.phraseSolved();//TODO foreign key language pair
        }
        phraseRepository.updateAttempt(phrase.getId(), attempt);

        return true;
    }

    private String updateCurrentText(Phrase phrase, Character character) {

        String solution = phrase.getSolution();
        IntStream indices = IntStream.range(0, solution.length()).filter(i -> StringUtils
                .equalsIgnoreCase(character.toString(), String.valueOf(solution.charAt(i))));
        StringBuilder newCurrentText = new StringBuilder(phrase.getAttempt());
        indices.forEach(i -> newCurrentText.setCharAt(i, character));
        return newCurrentText.toString();
    }

    private boolean isLetterCorrect(Phrase solvablePhrase, Character character) {

        return StringUtils.containsIgnoreCase(solvablePhrase.getSolution(), character.toString()) &&
                !StringUtils.containsIgnoreCase(solvablePhrase.getAttempt(), character.toString());
    }

    public LiveData<List<Phrase>> getPhrases(Locale locale) {

        if (phrases == null) {
            phrases = phraseRepository.findAllPhrases(locale);
        }
        return phrases;
    }
}
