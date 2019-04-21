package com.alexkn.syntact.dataaccess.language;

import com.alexkn.syntact.domain.model.LanguagePair;

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
public interface LanguagePairDao {

    @Insert
    Long insert(LanguagePairEntity activeLanguagePairEntity);

    @Insert
    void insert(Collection<LanguagePairEntity> activeLanguagePairEntities);

    @Query("DELETE FROM LanguagePair where id = :id")
    void delete(Long id);

    @Update
    void update(LanguagePairEntity languagePairEntity);

    @Query("SELECT EXISTS(SELECT 1 FROM languagepair WHERE languageLeft = :left AND languageRight = :right)")
    boolean languagePairExists(Locale left, Locale right);

    @Query("SELECT * FROM LanguagePair where id = :id")
    LanguagePairEntity find(Long id);

    @Query("SELECT * FROM LanguagePair")
    LiveData<List<LanguagePairEntity>> findAll();

    @Query("SELECT * FROM LanguagePair WHERE id = :id LIMIT 1")
    LiveData<LanguagePairEntity> findLanguagePair(Long id);

    @Query("UPDATE LanguagePair SET score = :newScore WHERE id = :id")
    void updateScore(Long id, int newScore);

    @Query("UPDATE LanguagePair SET score = score + :by WHERE id = :id")
    void incrementScore(Long id, int by);
}
