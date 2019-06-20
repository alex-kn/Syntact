package com.alexkn.syntact.data.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.alexkn.syntact.data.dao.base.BaseDao;
import com.alexkn.syntact.data.model.Clue;

@Dao
public interface ClueDao extends BaseDao<Clue> {

    @Query("SELECT * FROM Clue where clueId = :id")
    Clue find(Long id);
}
