package com.alexkn.syntact.hangwords.ui;

import com.alexkn.syntact.hangwords.dataaccess.Phrase;
import com.alexkn.syntact.hangwords.logic.SolvablePhrase;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class HangwordsViewModel extends ViewModel {

    private final MutableLiveData<List<Phrase>> phrases = new MutableLiveData<>();

    private final MutableLiveData<List<Letter>> letters = new MutableLiveData<>();

    private final LiveData<List<SolvablePhrase>> solvablePhrases;

    public HangwordsViewModel() {

        ArrayList<Phrase> tmpPhrases = new ArrayList<>();
        tmpPhrases.add(new Phrase("Aktion", "Action"));
        tmpPhrases.add(new Phrase("Clown", "Clown"));
        tmpPhrases.add(new Phrase("Owen", "Owen"));
        tmpPhrases.add(new Phrase("Lewis", "Lewis"));
        tmpPhrases.add(new Phrase("Bier", "Beer"));
        tmpPhrases.add(new Phrase("Tee", "Tea"));
        tmpPhrases.add(new Phrase("Alle", "All"));
        tmpPhrases.add(new Phrase("Wackeln", "Wiggle"));
        tmpPhrases.add(new Phrase("Warten", "Wait"));
        tmpPhrases.add(new Phrase("Niedrig", "Low"));
        tmpPhrases.add(new Phrase("Zeichentrickfilm", "Cartoon"));

        solvablePhrases = Transformations
                .map(phrases, HangwordsViewModel::convertyPhrasesToUiModel);


        List<Letter> letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".chars()
                .mapToObj(character -> new Letter(((char) character))).collect(Collectors.toList());
        this.letters.setValue(letters);

        phrases.setValue(tmpPhrases);
    }

    private static List<SolvablePhrase> convertyPhrasesToUiModel(List<Phrase> phrases) {
        return phrases.stream().map(phrase -> new SolvablePhrase(phrase.getId(), phrase.getClue(),
                phrase.getSolution(), StringUtils.repeat('_', phrase.getSolution().length())))
                .collect(Collectors.toList());
    }

    public LiveData<List<Phrase>> getPhrases() {
        return phrases;
    }

    public LiveData<List<Letter>> getLetters() {
        return letters;
    }

    public LiveData<List<SolvablePhrase>> getSolvablePhrases() {
        return solvablePhrases;
    }
}
