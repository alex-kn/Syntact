package com.alexkn.syntact.dataaccess.language;

import java.util.Collection;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface LanguagePairDao {

    @Insert
    void insert(LanguagePairEntity activeLanguagePairEntity);

    @Insert
    void insert(Collection<LanguagePairEntity> activeLanguagePairEntities);

    @Delete
    void delete(LanguagePairEntity activeLanguagePairEntity);

    @Query("SELECT * FROM LanguagePair")
    LiveData<List<LanguagePairEntity>> findAll();

    @Query("UPDATE LanguagePair SET score = :newScore WHERE id = :id")
    void updateScore(int id, int newScore);

}
