package com.alexkn.syntact.dataaccess.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.alexkn.syntact.dataaccess.dao.base.BaseDao;
import com.alexkn.syntact.domain.model.Clue;

@Dao
public interface ClueDao extends BaseDao<Clue> {

    @Query("SELECT * FROM Clue where clueId = :id")
    Clue find(Long id);
}
