package com.alexkn.syntact.dataaccess.letter;

import com.alexkn.syntact.domain.common.LetterColumn;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface LetterDao {

    @Insert
    void insert(List<LetterEntity> letterEntities);

    @Insert
    void insert(LetterEntity letterEntity);

    @Delete
    void delete(LetterEntity letterEntity);

    @Query("SELECT * FROM Letter WHERE letterColumn = :letterColumn and languagePairId = :languagePairId")
    LiveData<List<LetterEntity>> find(Long languagePairId, LetterColumn letterColumn);

    @Query("DELETE FROM Letter WHERE languagePairId = :languagePairId")
    void deleteAllLettersForLanguage(Long languagePairId);
}
