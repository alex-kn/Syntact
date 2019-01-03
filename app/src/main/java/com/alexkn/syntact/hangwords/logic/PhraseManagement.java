package com.alexkn.syntact.hangwords.logic;

import com.alexkn.syntact.hangwords.dataaccess.Phrase;
import com.alexkn.syntact.hangwords.ui.Letter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class PhraseManagement {
    private static final PhraseManagement ourInstance = new PhraseManagement();

    public static PhraseManagement getInstance() {
        return ourInstance;
    }

    private final MutableLiveData<List<Phrase>> phrases = new MutableLiveData<>();

    private PhraseManagement() {
        ArrayList<Phrase> tmpPhrases = new ArrayList<>();
        tmpPhrases.add(new Phrase("AB", "A"));
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
        tmpPhrases.get(5).setLastSolved(Instant.now());
        phrases.setValue(tmpPhrases);

    }

    public void solvePhrase(int id) {
        List<Phrase> value = new ArrayList<>(phrases.getValue());
        List<Phrase> collect = value.stream().peek(phrase1 -> {
            if (phrase1.getId() == id) {
                phrase1.setLastSolved(Instant.now());
            }
        }).collect(Collectors.toList());
        phrases.setValue(collect);
    }

    public Phrase findPhrase(int id) {
        return phrases.getValue().stream().filter(phrase -> phrase.getId() == id).findFirst().get();

    }

    public LiveData<List<Phrase>> getPhrases() {
        return phrases;
    }
}
