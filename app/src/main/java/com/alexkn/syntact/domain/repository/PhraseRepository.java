package com.alexkn.syntact.domain.repository;

import com.alexkn.syntact.dataaccess.phrase.PhraseEntity;

import java.time.Instant;
import java.util.List;

import androidx.lifecycle.LiveData;

public interface PhraseRepository {

    void updateLastSolved(int id, Instant lastSolved);

    LiveData<List<PhraseEntity>> getAllPhrases();

    void insert(PhraseEntity phraseEntity);

    void deleteAll();
}
