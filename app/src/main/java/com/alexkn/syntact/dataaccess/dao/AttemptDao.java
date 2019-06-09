package com.alexkn.syntact.dataaccess.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.alexkn.syntact.dataaccess.dao.base.BaseDao;
import com.alexkn.syntact.domain.model.Attempt;

@Dao
public interface AttemptDao extends BaseDao<Attempt> {

    @Query("UPDATE Attempt SET attemptText = :attempt WHERE attemptSolvableItemId = :solvableItemId")
    void updateAttempt(Long solvableItemId, String attempt);

    @Query("SELECT * FROM attempt where attemptId = :id")
    Attempt find(Long id);
}
