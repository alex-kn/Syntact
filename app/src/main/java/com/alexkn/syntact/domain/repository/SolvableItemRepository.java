package com.alexkn.syntact.domain.repository;

import androidx.lifecycle.LiveData;

import com.alexkn.syntact.domain.model.SolvableItem;
import com.alexkn.syntact.domain.model.cto.SolvableTranslationCto;
import com.alexkn.syntact.domain.repository.base.BaseRepository;

import java.time.Instant;
import java.util.List;

public interface SolvableItemRepository extends BaseRepository<SolvableItem> {

    LiveData<List<SolvableTranslationCto>> getSolvableTranslationsDueBefore(Long bucketId, Instant time);
}
