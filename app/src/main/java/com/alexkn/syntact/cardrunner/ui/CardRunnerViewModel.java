package com.alexkn.syntact.cardrunner.ui;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CardRunnerViewModel extends ViewModel {

    private final MutableLiveData<List<Phrase>> phrases = new MutableLiveData<>();

    public CardRunnerViewModel() {


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


        phrases.setValue(tmpPhrases);
    }

    public LiveData<List<Phrase>> getPhrases() {
        return phrases;
    }
}
