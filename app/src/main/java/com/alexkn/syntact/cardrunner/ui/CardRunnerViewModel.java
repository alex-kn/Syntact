package com.alexkn.syntact.cardrunner.ui;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModel;

public class CardRunnerViewModel extends ViewModel {

    public List<Phrase> getPhrases() {
        return phrases;
    }

    private final List<Phrase> phrases = new ArrayList<>();

    public CardRunnerViewModel() {
        phrases.add(new Phrase("Aktion", "Action"));
        phrases.add(new Phrase("Clown", "Clown"));
        phrases.add(new Phrase("Owen", "Owen"));
        phrases.add(new Phrase("Lewis", "Lewis"));
        phrases.add(new Phrase("Bier", "Beer"));
        phrases.add(new Phrase("Tee", "Tea"));
        phrases.add(new Phrase("Alle", "All"));
        phrases.add(new Phrase("Wackeln", "Wiggle"));
        phrases.add(new Phrase("Warten", "Wait"));
        phrases.add(new Phrase("Niedrig", "Low"));
        phrases.add(new Phrase("Zeichentrickfilm", "Cartoon"));
    }
}
