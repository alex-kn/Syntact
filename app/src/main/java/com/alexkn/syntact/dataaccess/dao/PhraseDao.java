package com.alexkn.syntact.dataaccess.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.alexkn.syntact.dataaccess.dao.base.BaseDao;
import com.alexkn.syntact.domain.model.SolvableTranslation;

import java.time.Instant;
import java.util.List;

@Dao
public interface PhraseDao extends BaseDao<SolvableTranslation> {

    @Query("SELECT * FROM SolvableTranslation WHERE nextDueDate < :time AND bucketId = :bucketId ORDER BY lastSolved LIMIT 10")
    LiveData<List<SolvableTranslation>> findPhrasesForBucketDueBefore(Long bucketId, Instant time);

    @Query("UPDATE SolvableTranslation SET attempt = :newAttempt WHERE id = :id")
    void updateAttempt(Long id, String newAttempt);

    @Query("SELECT SUM(timesSolved) FROM SolvableTranslation where bucketId = :bucketId")
    LiveData<Integer> getSumOfTimesSolved(Long bucketId);
}
