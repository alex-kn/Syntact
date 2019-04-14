package com.alexkn.syntact.domain.repository;

import com.alexkn.syntact.domain.model.Phrase;

import java.time.Instant;
import java.util.List;

import androidx.lifecycle.LiveData;

public interface PhraseRepository {

    void updateAttempt(Long id, String attempt);

    void update(Phrase phrase);

    LiveData<List<Phrase>> findPhrasesForLanguagePairDueBefore(Long languagePairId, Instant time);

    void insert(Phrase phrase);

    void insert(List<Phrase> phrases);
}
