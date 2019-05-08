package com.alexkn.syntact.dataaccess.letter;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.alexkn.syntact.domain.common.LetterColumn;
import com.alexkn.syntact.domain.model.Letter;

import java.util.List;

@Dao
public interface LetterDao {

    @Insert
    void insert(List<Letter> letterEntities);

    @Insert
    void insert(Letter letter);

    @Delete
    void delete(Letter letter);

    @Query("SELECT * FROM Letter WHERE letterColumn = :letterColumn and languagePairId = :languagePairId")
    LiveData<List<Letter>> find(Long languagePairId, LetterColumn letterColumn);

    @Query("DELETE FROM Letter WHERE languagePairId = :languagePairId")
    void deleteAllLettersForLanguage(Long languagePairId);
}
