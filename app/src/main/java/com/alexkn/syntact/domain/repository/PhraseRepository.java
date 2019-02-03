package com.alexkn.syntact.domain.repository;

import com.alexkn.syntact.domain.model.Phrase;

import java.time.Instant;
import java.util.List;
import java.util.Locale;

import androidx.lifecycle.LiveData;

public interface PhraseRepository {

    void updateLastSolved(int id, Instant lastSolved);

    void updateAttempt(int id, String attempt);

    LiveData<List<Phrase>> getAllPhrases(Locale locale);

    void insert(Phrase phrase);

    void insert(List<Phrase> phrases);

    void deleteAll();

    int count();
}
