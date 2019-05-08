package com.alexkn.syntact.dataaccess.phrase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alexkn.syntact.domain.model.Phrase;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Dao
public interface PhraseDao {

    @Insert
    void insert(Phrase phrase);

    @Insert
    void insert(Collection<Phrase> phraseEntities);

    @Update
    void update(Phrase phrase);

    @Delete
    void delete(Phrase phrase);

    @Query("SELECT * FROM Phrase WHERE nextDueDate < :time AND languagePairId = :languagePairId ORDER BY lastSolved LIMIT 10")
    LiveData<List<Phrase>> findPhrasesForLanguagePairDueBefore(Long languagePairId, Instant time);

    @Query("UPDATE Phrase SET attempt = :newAttempt WHERE id = :id")
    void updateAttempt(Long id, String newAttempt);
}
