package com.alexkn.syntact.dataaccess.phrase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alexkn.syntact.dataaccess.common.BaseDao;
import com.alexkn.syntact.domain.model.SolvableTranslation;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Dao
public interface PhraseDao extends BaseDao<SolvableTranslation> {

    @Query("SELECT * FROM SolvableTranslation WHERE nextDueDate < :time AND bucketId = :bucketId ORDER BY lastSolved LIMIT 10")
    LiveData<List<SolvableTranslation>> findPhrasesForBucketDueBefore(Long bucketId, Instant time);

    @Query("UPDATE SolvableTranslation SET attempt = :newAttempt WHERE id = :id")
    void updateAttempt(Long id, String newAttempt);
}
