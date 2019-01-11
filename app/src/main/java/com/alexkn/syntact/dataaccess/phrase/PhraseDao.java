package com.alexkn.syntact.dataaccess.phrase;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PhraseDao {

    @Insert
    public void insert(PhraseEntity phraseEntity);

    @Insert
    public void insert(Collection<PhraseEntity> phraseEntities);

    @Update
    public void update(PhraseEntity phraseEntity);

    @Delete
    public void delete(PhraseEntity phraseEntity);

    @Query("SELECT * FROM Phrase")
    public LiveData<List<PhraseEntity>> findAll();

    @Query("UPDATE Phrase SET lastSolved = :newLastSolved WHERE id = :id")
    public void updateLastSolved(int id, Instant newLastSolved);

    @Query("DELETE FROM Phrase")
    void deleteAll();
}
