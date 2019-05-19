package com.alexkn.syntact.domain.repository;

import com.alexkn.syntact.domain.model.SolvableItem;

import java.time.Instant;
import java.util.List;

import androidx.lifecycle.LiveData;

public interface PhraseRepository {

    void updateAttempt(Long id, String attempt);

    void update(SolvableItem solvableItem);

    LiveData<List<SolvableItem>> findPhrasesForBucketDueBefore(Long bucketId, Instant time);

    void insert(SolvableItem solvableItem);

    void insert(List<SolvableItem> solvableItems);
}
