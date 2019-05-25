package com.alexkn.syntact.domain.repository;

import com.alexkn.syntact.domain.model.SolvableItem;
import com.alexkn.syntact.domain.model.SolvableTranslation;

import java.time.Instant;
import java.util.List;

import androidx.lifecycle.LiveData;

public interface PhraseRepository {

    void updateAttempt(Long id, String attempt);

    void update(SolvableTranslation solvableTranslation);

    LiveData<List<SolvableTranslation>> findPhrasesForBucketDueBefore(Long bucketId, Instant time);

    void insert(SolvableTranslation solvableTranslation);

    void insert(List<SolvableTranslation> solvableTranslations);
}
