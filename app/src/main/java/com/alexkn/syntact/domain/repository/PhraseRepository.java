package com.alexkn.syntact.domain.repository;

import com.alexkn.syntact.domain.model.Phrase;

import java.time.Instant;
import java.util.List;
import java.util.Locale;

import androidx.lifecycle.LiveData;

public interface PhraseRepository {

    void updateLastSolved(Long id, Instant lastSolved);

    void updateAttempt(Long id, String attempt);

    LiveData<List<Phrase>> findAllPhrases(Locale locale);

    LiveData<List<Phrase>> findPhrasesForLanguagePairDueBefore(Long languagePairId, Instant time);

    void insert(Phrase phrase);

    void insert(List<Phrase> phrases);

    void deleteAll();

    int count();
}
