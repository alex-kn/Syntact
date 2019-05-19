package com.alexkn.syntact.domain.usecase;

import android.app.Application;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.SolvableItem;
import com.alexkn.syntact.domain.repository.PhraseRepository;
import com.alexkn.syntact.domain.service.PhraseGenerator;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    public void makeAttempt(SolvableItem solvableItem, Character character) {

        String attempt = updateCurrentAttempt(solvableItem, character);
        if (!attempt.contains(application.getString(R.string.empty))) {
            solvePhrase(solvableItem);
        } else {
            phraseRepository.updateAttempt(solvableItem.getId(), attempt);
        }
    }

    private void solvePhrase(SolvableItem solvableItem) {

        int performanceRating = 3;
        float easiness = solvableItem.getEasiness();
        easiness += -0.80 + 0.28 * performanceRating + 0.02 * performanceRating * performanceRating;

        int consecutiveCorrectAnswers = solvableItem.getConsecutiveCorrectAnswers() + 1;

        double daysToAdd = 6 * Math.pow(easiness, consecutiveCorrectAnswers - 1d);
        Instant nextDueDate = Instant.now().plus(Math.round(daysToAdd), ChronoUnit.DAYS);

        solvableItem.setEasiness(easiness);
        solvableItem.setConsecutiveCorrectAnswers(consecutiveCorrectAnswers);
        solvableItem.setNextDueDate(nextDueDate);
        solvableItem.setLastSolved(Instant.now());
        solvableItem.setAttempt(StringUtils
                .repeat(application.getString(R.string.empty), solvableItem.getSolution().getText().length()));

        phraseRepository.update(solvableItem);
    }

    private String updateCurrentAttempt(SolvableItem solvableItem, Character character) {

        String solution = solvableItem.getSolution().getText();

        IntStream indices = IntStream.range(0, solution.length()).filter(i -> StringUtils
                .equalsIgnoreCase(character.toString(), String.valueOf(solution.charAt(i))));
        StringBuilder newCurrentText = new StringBuilder(solvableItem.getAttempt());
        indices.forEach(i -> newCurrentText.setCharAt(i, character));
        return newCurrentText.toString();
    }

    public boolean isLetterCorrect(SolvableItem solvableSolvableItem, Character character) {

        return StringUtils.containsIgnoreCase(solvableSolvableItem.getSolution().getText(), character.toString()) &&
                !StringUtils.containsIgnoreCase(solvableSolvableItem.getAttempt(), character.toString());
    }

    public LiveData<List<SolvableItem>> getPhrases(Long bucketId) {

        return phraseRepository.findPhrasesForBucketDueBefore(bucketId, Instant.now());
    }

    public void initializePhrases(Long insertedLanguageId, Locale languageLeft,
            Locale languageRight) {

        List<Character> specialCharacters = Arrays.asList('?', '\'', ',', '.', '-', ' ', ';');

        if (languageLeft.equals(Locale.GERMAN) && languageRight.equals(Locale.ENGLISH)) {
            List<SolvableItem> solvableItems = phraseGenerator.generateGermanEnglishPhrases();
            solvableItems.forEach(phrase -> {
                phrase.setBucketId(insertedLanguageId);
                specialCharacters.forEach(
                        character -> phrase.setAttempt(updateCurrentAttempt(phrase, character)));
            });
            Collections.shuffle(solvableItems);
            phraseRepository.insert(solvableItems);
        }
    }
}
