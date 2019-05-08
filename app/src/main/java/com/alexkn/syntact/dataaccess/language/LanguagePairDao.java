package com.alexkn.syntact.dataaccess.language;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alexkn.syntact.domain.model.LanguagePair;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Dao
public interface LanguagePairDao {

    @Insert
    Long insert(LanguagePair activeLanguagePair);

    @Insert
    void insert(Collection<LanguagePair> activeLanguagePairEntities);

    @Query("DELETE FROM LanguagePair where id = :id")
    void delete(Long id);

    @Update
    void update(LanguagePair languagePair);

    @Query("SELECT EXISTS(SELECT 1 FROM languagepair WHERE languageLeft = :left AND languageRight = :right)")
    boolean languagePairExists(Locale left, Locale right);

    @Query("SELECT * FROM LanguagePair where id = :id")
    LanguagePair find(Long id);

    @Query("SELECT * FROM LanguagePair")
    LiveData<List<LanguagePair>> findAll();

    @Query("SELECT * FROM LanguagePair WHERE id = :id LIMIT 1")
    LiveData<LanguagePair> findLanguagePair(Long id);

    @Query("UPDATE LanguagePair SET score = :newScore WHERE id = :id")
    void updateScore(Long id, int newScore);

    @Query("UPDATE LanguagePair SET score = score + :by WHERE id = :id")
    void incrementScore(Long id, int by);
}
