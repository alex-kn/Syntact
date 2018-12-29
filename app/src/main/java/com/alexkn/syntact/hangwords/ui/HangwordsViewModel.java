package com.alexkn.syntact.hangwords.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HangwordsViewModel extends ViewModel {

    private final MutableLiveData<List<Phrase>> phrases = new MutableLiveData<>();

    private final MutableLiveData<List<Letter>> letters = new MutableLiveData<>();

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

        letters.setValue(Arrays.asList(new Letter('A'), new Letter('L'), new Letter('P'), new Letter
                ('O'), new Letter('W'), new Letter('C'), new Letter('D'), new Letter('E'), new
                Letter
                ('Z'), new Letter('Y'), new Letter('Q'), new Letter('B')));


        phrases.setValue(tmpPhrases);
    }

    public LiveData<List<Phrase>> getPhrases() {
        return phrases;
    }

    public LiveData<List<Letter>> getLetters() {
        return letters;
    }
}
