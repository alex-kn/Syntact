package com.alexkn.syntact.domain.util;

import android.app.Application;
import android.util.Log;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.Clue;
import com.alexkn.syntact.domain.model.Solution;
import com.alexkn.syntact.domain.model.SolvableItem;
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

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PhraseGenerator {

    private static final String TAG = PhraseGenerator.class.getSimpleName();

    @Inject
    Application application;

    @Inject
    PhraseGenerator() {}

    public List<SolvableItem> createSolvabeItems(Long bucketId,
            List<PhraseResponse> phraseResponses) {

        return phraseResponses.stream()
                .map(phraseResponse -> createSolvableItem(bucketId, phraseResponse))
                .collect(Collectors.toList());
    }

    private SolvableItem createSolvableItem(Long bucketId, PhraseResponse phrase) {

        SolvableItem solvableItem = new SolvableItem();

        String phraseText = phrase.getText();
        String translationText = phrase.getTranslations().get(0).getText();
        Locale translationLanguage = phrase.getTranslations().get(0).getLanguage();
        solvableItem.setSolution(new Solution(phraseText, Locale.ENGLISH));
        solvableItem.setClue(new Clue(translationText, translationLanguage));
        solvableItem.setAttempt(StringUtils
                .repeat(application.getString(R.string.empty), phraseText.length()));

        solvableItem.setBucketId(bucketId);
        solvableItem.setConsecutiveCorrectAnswers(0);
        solvableItem.setEasiness(2.5f);
        solvableItem.setNextDueDate(Instant.now());
        solvableItem.setTimesSolved(0);
        solvableItem.setPhraseId(phrase.getId());
        return solvableItem;
    }

    public List<SolvableItem> generateGermanEnglishPhrases() {

        ArrayList<SolvableItem> solvableItems = new ArrayList<>();

        try {

            try (InputStream open = application.getAssets().open("phrases_german_english.json")) {

                String s = IOUtil.toString(open, "UTF-8");
                JSONArray frequencyList = new JSONArray(s);
                for (int i = 0; i < frequencyList.length(); i++) {
                    JSONObject jsonObject = frequencyList.getJSONObject(i);
                    String clueText = jsonObject.getString("clue");
                    String solutionText = jsonObject.getString("solution");
                    SolvableItem solvableItem = new SolvableItem();

                    Clue clue = new Clue();
                    clue.setText(clueText);
                    clue.setLanguage(Locale.GERMAN);
                    Solution solution = new Solution();
                    solution.setText(solutionText);
                    solution.setLanguage(Locale.ENGLISH);

                    solvableItem.setClue(clue);
                    solvableItem.setSolution(solution);
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
