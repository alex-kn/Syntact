package com.alexkn.syntact.dataaccess.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.alexkn.syntact.dataaccess.dao.base.BaseDao;
import com.alexkn.syntact.domain.model.SolvableItem;
import com.alexkn.syntact.domain.model.cto.SolvableTranslationCto;

import java.time.Instant;
import java.util.List;

@Dao
public interface SolvableItemDao extends BaseDao<SolvableItem> {

    //    @Query("SELECT * FROM solvableitem s, clue c, attempt a where s.id = c.solvableItemId AND s.id = a.solvableItemId AND s.nextDueDate < :time AND s.bucketId = :bucketId ")
    //    LiveData<List<SolvableTranslationCto>> getSolvableTranslationsDueBefore(Long bucketId, Instant time);

    @Query("SELECT * FROM solvableitem s JOIN clue c ON (s.id = c.clueSolvableItemId) JOIN attempt a ON (s.id = a.attemptSolvableItemId) WHERE s.nextDueDate <= :time AND s.bucketId = :bucketId ")
    LiveData<List<SolvableTranslationCto>> getSolvableTranslationsDueBefore(Long bucketId, Instant time);

    @Query("SELECT MAX(id) FROM solvableitem WHERE bucketId = :bucketId;")
    long getMaxId(long bucketId);

    @Query("SELECT * FROM SolvableItem where id = :id")
    SolvableItem find(Long id);
}
