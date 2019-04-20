package com.alexkn.syntact.domain.usecase;

import android.app.Application;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.Phrase;
import com.alexkn.syntact.domain.repository.PhraseRepository;
import com.alexkn.syntact.domain.service.PhraseGenerator;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;

@Singleton
public class ManagePhrases {

    @Inject
    PhraseRepository phraseRepository;

    @Inject
    Application application;

    @Inject
    PhraseGenerator phraseGenerator;

    @Inject
    ManageScore manageScore;

    @Inject
    ManagePhrases() { }

    public boolean makeAttempt(Phrase phrase, Character character) {

        if (!isLetterCorrect(phrase, character)) {
            return false;
        }
        String attempt = updateCurrentAttempt(phrase, character);
        if (!attempt.contains(application.getString(R.string.empty))) {
            solvePhrase(phrase);
        } else {
            phraseRepository.updateAttempt(phrase.getId(), attempt);
        }

        return true;
    }

    private void solvePhrase(Phrase phrase) {

        int performanceRating = 3;
        float easiness = phrase.getEasiness();
        easiness += -0.80 + 0.28 * performanceRating + 0.02 * performanceRating * performanceRating;

        int consecutiveCorrectAnswers = phrase.getConsecutiveCorrectAnswers() + 1;

        double daysToAdd = 6 * Math.pow(easiness, consecutiveCorrectAnswers - 1d);
        Instant nextDueDate = Instant.now().plus(Math.round(daysToAdd), ChronoUnit.DAYS);

        phrase.setEasiness(easiness);
        phrase.setConsecutiveCorrectAnswers(consecutiveCorrectAnswers);
        phrase.setNextDueDate(nextDueDate);
        phrase.setLastSolved(Instant.now());
        phrase.setAttempt(StringUtils
                .repeat(application.getString(R.string.empty), phrase.getSolution().length()));

        phraseRepository.update(phrase);
        manageScore.phraseSolved(phrase);
    }

    private String updateCurrentAttempt(Phrase phrase, Character character) {

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

    public LiveData<List<Phrase>> getPhrases(Long languagePairId) {

        return phraseRepository.findPhrasesForLanguagePairDueBefore(languagePairId, Instant.now());
    }

    public void generateGermanEnglishPhrases(Long insertedLanguageId, Locale languageLeft,
            Locale languageRight) {

        ArrayList<Phrase> phrases = phraseGenerator.generateGermanEnglishPhrases();
        phrases.forEach(phrase -> {
            phrase.setLanguagePairId(insertedLanguageId);
            phrase.setAttempt(updateCurrentAttempt(phrase, '?'));
            phrase.setAttempt(updateCurrentAttempt(phrase, '.'));
            phrase.setAttempt(updateCurrentAttempt(phrase, ','));
            phrase.setAttempt(updateCurrentAttempt(phrase, ' '));
        });
        phraseRepository.insert(phrases);
    }
}
