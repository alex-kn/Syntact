package com.alexkn.syntact.domain.util;

import android.app.Application;
import android.util.Log;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.Clue;
import com.alexkn.syntact.domain.model.Solution;
import com.alexkn.syntact.domain.model.SolvableItem;
import com.alexkn.syntact.domain.model.SolvableTranslation;
import com.alexkn.syntact.restservice.PhraseResponse;

import org.apache.commons.io.IOUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PhraseGenerator {

    private static final String TAG = PhraseGenerator.class.getSimpleName();

    @Inject
    Application application;

    @Inject
    PhraseGenerator() {}

    public List<SolvableTranslation> createSolvabeItems(Long bucketId,
            List<PhraseResponse> phraseResponses) {

        return phraseResponses.stream()
                .flatMap(phraseResponse -> createSolvableTranslation(bucketId, phraseResponse))
                .collect(Collectors.toList());
    }

    private Stream<SolvableTranslation> createSolvableTranslation(Long bucketId, PhraseResponse phrase) {

        SolvableTranslation solvableTranslation1 = new SolvableTranslation();

        String phraseText = phrase.getText();
        String translationText = phrase.getTranslations().get(0).getText();

        solvableTranslation1.setSolution(phraseText);
        solvableTranslation1.setClue(translationText);
        solvableTranslation1.setAttempt(StringUtils
                .repeat(application.getString(R.string.empty), phraseText.length()));

        solvableTranslation1.setBucketId(bucketId);
        solvableTranslation1.setConsecutiveCorrectAnswers(0);
        solvableTranslation1.setEasiness(2.5f);
        solvableTranslation1.setNextDueDate(Instant.now());
        solvableTranslation1.setTimesSolved(0);
        solvableTranslation1.setPhraseId(phrase.getId());

        SolvableTranslation solvableTranslation2 = new SolvableTranslation();

        solvableTranslation2.setSolution(translationText);
        solvableTranslation2.setClue(phraseText);
        solvableTranslation2.setAttempt(StringUtils
                .repeat(application.getString(R.string.empty), phraseText.length()));

        solvableTranslation2.setBucketId(bucketId);
        solvableTranslation2.setConsecutiveCorrectAnswers(0);
        solvableTranslation2.setEasiness(2.5f);
        solvableTranslation2.setNextDueDate(Instant.now());
        solvableTranslation2.setTimesSolved(0);
        solvableTranslation2.setPhraseId(phrase.getId());

        return Stream.of(solvableTranslation1, solvableTranslation2);
    }

    public List<SolvableTranslation> generateGermanEnglishPhrases() {

        ArrayList<SolvableTranslation> solvableItems = new ArrayList<>();

        try {
            //todo remove
            try (InputStream open = application.getAssets().open("phrases_german_english.json")) {

                String s = IOUtil.toString(open, "UTF-8");
                JSONArray frequencyList = new JSONArray(s);
                for (int i = 0; i < frequencyList.length(); i++) {
                    JSONObject jsonObject = frequencyList.getJSONObject(i);
                    String clueText = jsonObject.getString("clue");
                    String solutionText = jsonObject.getString("solution");
                    SolvableTranslation solvableItem = new SolvableTranslation();

                    Clue clue = new Clue();
                    clue.setText(clueText);
                    clue.setLanguage(Locale.GERMAN);
                    Solution solution = new Solution();
                    solution.setText(solutionText);
                    solution.setLanguage(Locale.ENGLISH);

                    solvableItem.setClue(clueText);
                    solvableItem.setSolution(solutionText);
                    solvableItem.setAttempt(StringUtils
                            .repeat(application.getString(R.string.empty), solutionText.length()));
                    solvableItem.setConsecutiveCorrectAnswers(0);
                    solvableItem.setEasiness(2.5f);
                    solvableItem.setNextDueDate(Instant.now());
                    solvableItem.setTimesSolved(0);
                    solvableItems.add(solvableItem);
                }
            }
        } catch (Exception e) {

            Log.e(TAG, "GenerateCharactersUseCase: Error reading JSON", e);
        }
        return solvableItems;
    }

    public List<SolvableItem> generatePhrasesForLocale(Locale left, Locale right) {

        return Collections.emptyList();
    }
}
