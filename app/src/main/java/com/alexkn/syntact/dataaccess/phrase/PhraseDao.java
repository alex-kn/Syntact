package com.alexkn.syntact.dataaccess.phrase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alexkn.syntact.domain.model.SolvableItem;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Dao
public interface PhraseDao {

    @Insert
    void insert(SolvableItem solvableItem);

    @Insert
    void insert(Collection<SolvableItem> solvableItemEntities);

    @Update
    void update(SolvableItem solvableItem);

    @Delete
    void delete(SolvableItem solvableItem);

    @Query("SELECT * FROM SolvableItem WHERE nextDueDate < :time AND bucketId = :bucketId ORDER BY lastSolved LIMIT 10")
    LiveData<List<SolvableItem>> findPhrasesForBucketDueBefore(Long bucketId, Instant time);

    @Query("UPDATE SolvableItem SET attempt = :newAttempt WHERE id = :id")
    void updateAttempt(Long id, String newAttempt);
}
