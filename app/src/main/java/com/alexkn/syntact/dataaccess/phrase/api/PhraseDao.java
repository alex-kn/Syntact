package com.alexkn.syntact.dataaccess.phrase.api;

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

    @Query("SELECT COUNT(*) FROM Phrase")
    int count();

    @Query("SELECT * FROM Phrase ORDER BY lastSolved")
    LiveData<List<PhraseEntity>> findAll();

    @Query("SELECT * FROM Phrase WHERE clueLocale = :locale OR solutionLocale = :locale ORDER BY lastSolved")
    LiveData<List<PhraseEntity>> findAll(Locale locale);

    @Query("UPDATE Phrase SET lastSolved = :newLastSolved WHERE id = :id")
    void updateLastSolved(int id, Instant newLastSolved);

    @Query("DELETE FROM Phrase")
    void deleteAll();

    @Query("UPDATE Phrase SET attempt = :newAttempt WHERE id = :id")
    void updateAttempt(int id, String newAttempt);
}
