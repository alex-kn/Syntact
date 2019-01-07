package com.alexkn.syntact.hangwords.logic;

import android.os.AsyncTask;

import com.alexkn.syntact.hangwords.ui.util.Letter;

import org.apache.commons.lang3.RandomStringUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class LetterManagement {

    private List<MutableLiveData<List<Letter>>> buckets;

    public LetterManagement(int numberOfBuckets) {

        buckets = Arrays.asList(new MutableLiveData<>(new ArrayList<>()),
                new MutableLiveData<>(new ArrayList<>()), new MutableLiveData<>(new ArrayList<>()));
        popuateLetters();
    }

    public void removeLetter(Letter letter) {

        buckets.forEach(liveData -> {
            List<Letter> letters = Objects.requireNonNull(liveData.getValue());
            letters.remove(letter);
            liveData.setValue(letters);
        });
        popuateLetters();
    }

    private void popuateLetters() {

        buckets.forEach(liveData -> {
            List<Letter> letters = Objects.requireNonNull(liveData.getValue());
            while (letters.size() < 20) {
                letters.add(generateRandomLetter());
            }
            liveData.setValue(letters);
        });
    }

    public Letter findLetter(int id) {

        ArrayList<Letter> letters = new ArrayList<>();
        buckets.forEach(liveData -> letters.addAll(Objects.requireNonNull(liveData.getValue())));

        Optional<Letter> letter = letters.stream().filter(l -> l.getId() == id).findFirst();
        if (letter.isPresent()) {
            return letter.get();
        } else {
            throw new RuntimeException("Letter not found.");
        }
    }

    private Letter generateRandomLetter() {

        return new Letter(RandomStringUtils.randomAlphabetic(1).charAt(0));
    }

    public LiveData<List<Letter>> getLetters(int bucket) {

        return buckets.get(bucket);
    }
}
