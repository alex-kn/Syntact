package com.alexkn.syntact.domain.service;

import android.app.Application;
import android.util.Log;

import com.alexkn.syntact.R;
import com.alexkn.syntact.domain.model.Phrase;

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

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PhraseGenerator {

    private static final String TAG = PhraseGenerator.class.getSimpleName();

    @Inject
    Application application;

    @Inject
    PhraseGenerator() {}

    public List<Phrase> generateGermanEnglishPhrases() {

        ArrayList<Phrase> phrases = new ArrayList<>();

        try {

            try (InputStream open = application.getAssets().open("phrases_german_english.json")) {

                String s = IOUtil.toString(open, "UTF-8");
                JSONArray frequencyList = new JSONArray(s);
                for (int i = 0; i < frequencyList.length(); i++) {
                    JSONObject jsonObject = frequencyList.getJSONObject(i);
                    String clue = jsonObject.getString("clue");
                    String solution = jsonObject.getString("solution");
                    Phrase phrase = new Phrase();
                    phrase.setClue(clue);
                    phrase.setSolution(solution);
                    phrase.setAttempt(StringUtils.repeat(application.getString(R.string.empty),
                            phrase.getSolution().length()));
                    phrase.setConsecutiveCorrectAnswers(0);
                    phrase.setEasiness(2.5f);
                    phrase.setNextDueDate(Instant.now());
                    phrase.setClueLocale(Locale.GERMAN);
                    phrase.setSolutionLocale(Locale.ENGLISH);
                    phrase.setTimesSolved(0);
                    phrases.add(phrase);
                }
            }
        } catch (Exception e) {

            Log.e(TAG, "GenerateCharactersUseCase: Error reading JSON", e);
        }
        return phrases;
    }

    public List<Phrase> generatePhrasesForLocale(Locale left, Locale right) {

        return Collections.emptyList();
    }
}
