package com.alexkn.syntact.domain.service;

import android.app.Application;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LetterGenerator {

    private static final String TAG = LetterGenerator.class.getSimpleName();

    private EnumeratedDistribution<Character> letterDistribution;

    @Inject
    LetterGenerator(Application application) {

        List<Pair<Character, Double>> letterFrequencyList = new ArrayList<>();
        try {

            try (InputStream open = application.getAssets().open("letterfrequencies.json")) {

                String s = IOUtils.toString(open, "UTF-8");
                JSONArray letters = new JSONArray(s);
                for (int i = 0; i < letters.length(); i++) {
                    JSONObject jsonObject = letters.getJSONObject(i);
                    Character letter = jsonObject.getString("letter").charAt(0);
                    Double frequency = jsonObject.getDouble("frequency");
                    letterFrequencyList.add(new Pair<>(letter, frequency));
                }
            }
        } catch (Exception e) {

            Log.e(TAG, "GenerateCharactersUseCase: Error reading JSON", e);
        }

        letterDistribution = new EnumeratedDistribution<>(letterFrequencyList);
    }

    public Character generateNewCharacter() {

        return letterDistribution.sample();
    }

    public Character[] generateCharacters(int sampleSize) {

        return letterDistribution.sample(sampleSize, new Character[sampleSize]);


    }
}
