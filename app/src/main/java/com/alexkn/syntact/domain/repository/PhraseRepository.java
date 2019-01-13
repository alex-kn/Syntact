package com.alexkn.syntact.domain.repository;

import com.alexkn.syntact.dataaccess.phrase.PhraseEntity;
import com.alexkn.syntact.domain.model.Phrase;

import java.time.Instant;
import java.util.List;

import androidx.lifecycle.LiveData;

public interface PhraseRepository {

    void updateLastSolved(int id, Instant lastSolved);

    void updateAttempt(int id, String attempt);

    LiveData<List<Phrase>> getAllPhrases();

    void insert(PhraseEntity phraseEntity);

    void deleteAll();
}
