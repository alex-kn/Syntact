package com.alexkn.syntact.hangwords.logic;

import com.alexkn.syntact.hangwords.ui.Letter;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class LetterManagement {

    private static final LetterManagement ourInstance = new LetterManagement();

    public static LetterManagement getInstance() {
        return ourInstance;
    }

    private final MutableLiveData<List<Letter>> letters1 = new MutableLiveData<>();
    private final MutableLiveData<List<Letter>> letters2 = new MutableLiveData<>();
    private final MutableLiveData<List<Letter>> letters3 = new MutableLiveData<>();

    private LetterManagement() {
        this.letters1.setValue(generateLetters());
        this.letters2.setValue(generateLetters());
        this.letters3.setValue(generateLetters());
    }

    private List<Letter> generateLetters() {
        List<Letter> letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".chars()
                .mapToObj(character -> new Letter(((char) character))).collect(Collectors.toList());
        Collections.shuffle(letters);
        return  letters.subList(0, 8);
    }

    public Letter generateRandomLetter(){
        return new Letter(RandomStringUtils.randomAlphabetic(1).charAt(0));
    }

    public Letter findLetter(int id) {
        ArrayList<Letter> letters = new ArrayList<>();
        letters.addAll(letters1.getValue());
        letters.addAll(letters2.getValue());
        letters.addAll(letters3.getValue());
        return letters.stream().filter(letter -> letter.getId() == id).findFirst().get();
    }

    public void removeLetter(int id) {
        Letter letter = findLetter(id);
        List<Letter> value1 = new ArrayList<>(letters1.getValue());
        if(value1.remove(letter)){
            value1.add(generateRandomLetter());
            letters1.setValue(value1);
            return;
        }

        List<Letter> value2 = new ArrayList<>(letters2.getValue());
        if(value2.remove(letter)){
            value2.add(generateRandomLetter());
            letters2.setValue(value2);
            return;
        }

        List<Letter> value3 = new ArrayList<>(letters3.getValue());
        if(value3.remove(letter)){
            value3.add(generateRandomLetter());
            letters3.setValue(value3);
        }
    }

    public LiveData<List<Letter>> getLetters1() {
        return letters1;
    }

    public LiveData<List<Letter>> getLetters2() {
        return letters2;
    }

    public LiveData<List<Letter>> getLetters3() {
        return letters3;
    }
}
