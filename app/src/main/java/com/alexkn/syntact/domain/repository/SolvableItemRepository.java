package com.alexkn.syntact.domain.repository;

import androidx.lifecycle.LiveData;

import com.alexkn.syntact.domain.model.SolvableItem;
import com.alexkn.syntact.domain.model.cto.SolvableTranslationCto;
import com.alexkn.syntact.domain.repository.base.BaseRepository;

import java.time.Instant;
import java.util.List;

import io.reactivex.Maybe;

public interface SolvableItemRepository extends BaseRepository<SolvableItem> {

    LiveData<List<SolvableTranslationCto>> getSolvableTranslationsDueBefore(Long bucketId, Instant time);

    Long getMaxId(Long bucketId);

    Maybe<SolvableTranslationCto[]> getNextTranslationDueBefore(long bucketId, Instant time, int count);
}
