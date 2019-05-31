package com.alexkn.syntact.dataaccess.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.alexkn.syntact.dataaccess.dao.base.BaseDao;
import com.alexkn.syntact.domain.common.LetterColumn;
import com.alexkn.syntact.domain.model.Letter;

import java.util.List;

@Dao
public interface LetterDao  extends BaseDao<Letter> {

    @Query("SELECT * FROM Letter WHERE letterColumn = :letterColumn and bucketId = :bucketId")
    LiveData<List<Letter>> find(Long bucketId, LetterColumn letterColumn);

    @Query("DELETE FROM Letter WHERE bucketId = :bucketId")
    void deleteAllLettersForLanguage(Long bucketId);
}