package com.alexkn.syntact.dataaccess.phrase;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PhraseDao {

    @Insert
    void insert(PhraseEntity phraseEntity);

    @Insert
    void insert(Collection<PhraseEntity> phraseEntities);

    @Update
    void update(PhraseEntity phraseEntity);

    @Delete
    void delete(PhraseEntity phraseEntity);

    @Query("SELECT * FROM Phrase WHERE nextDueDate < :time AND languagePairId = :languagePairId ORDER BY lastSolved LIMIT 10")
    LiveData<List<PhraseEntity>> findPhrasesForLanguagePairDueBefore(Long languagePairId, Instant time);

    @Query("UPDATE Phrase SET attempt = :newAttempt WHERE id = :id")
    void updateAttempt(Long id, String newAttempt);
}
