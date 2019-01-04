package com.alexkn.syntact.hangwords.ui;

import com.alexkn.syntact.hangwords.logic.SolvablePhrase;
import com.alexkn.syntact.hangwords.logic.SolvablePhrasesManagement;
import com.alexkn.syntact.hangwords.ui.util.Letter;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HangwordsViewModel extends ViewModel {

    private final MutableLiveData<List<Letter>> letters1 = new MutableLiveData<>(
            Collections.emptyList());

    private final MutableLiveData<List<Letter>> letters2 = new MutableLiveData<>(
            Collections.emptyList());

    private final MutableLiveData<List<Letter>> letters3 = new MutableLiveData<>(
            Collections.emptyList());

    private final SolvablePhrasesManagement solvablePhrasesManagement = new SolvablePhrasesManagement();

    public HangwordsViewModel() {

        this.letters1.setValue(generateLetters());
        this.letters2.setValue(generateLetters());
        this.letters3.setValue(generateLetters());
    }

    boolean solve(SolvablePhrase solvablePhrase, Integer letterId) {

        Letter letter = findLetter(letterId);
        boolean successful = solvablePhrasesManagement.solvePhrase(solvablePhrase, letter);
        if (successful) replaceLetter(letterId);

        return successful;
    }

    private List<Letter> generateLetters() {

        List<Letter> letters = "ACTIONAALOWCLOWNAL".chars()
                .mapToObj(character -> new Letter(((char) character))).collect(Collectors.toList());
        Collections.shuffle(letters);
        return letters.subList(0, 12);
    }

    private void replaceLetter(int id) {

        Letter letter = findLetter(id);
        List<Letter> value1 = new ArrayList<>(Objects.requireNonNull(letters1.getValue()));
        if (value1.remove(letter)) {
            value1.add(generateRandomLetter());
            letters1.setValue(value1);
            return;
        }

        List<Letter> value2 = new ArrayList<>(Objects.requireNonNull(letters2.getValue()));
        if (value2.remove(letter)) {
            value2.add(generateRandomLetter());
            letters2.setValue(value2);
            return;
        }

        List<Letter> value3 = new ArrayList<>(Objects.requireNonNull(letters3.getValue()));
        if (value3.remove(letter)) {
            value3.add(generateRandomLetter());
            letters3.setValue(value3);
        }
    }

    private Letter findLetter(int id) {

        ArrayList<Letter> letters = new ArrayList<>();
        letters.addAll(letters1.getValue());
        letters.addAll(letters2.getValue());
        letters.addAll(letters3.getValue());
        return letters.stream().filter(letter -> letter.getId() == id).findFirst().get();
    }

    private Letter generateRandomLetter() {

        return new Letter(RandomStringUtils.randomAlphabetic(1).charAt(0));
    }

    LiveData<List<Letter>> getLetters1() {

        return letters1;
    }

    LiveData<List<Letter>> getLetters2() {

        return letters2;
    }

    LiveData<List<Letter>> getLetters3() {

        return letters3;
    }

    LiveData<List<SolvablePhrase>> getSolvablePhrases() {

        return solvablePhrasesManagement.getSolvablePhrasesLiveData();
    }
}
