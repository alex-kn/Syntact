package com.alexkn.syntact.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.alexkn.syntact.core.model.Clue
import com.alexkn.syntact.data.dao.base.BaseDao

@Dao
interface ClueDao : BaseDao<Clue> {

    @Query("SELECT * FROM Clue where clueId = :id")
    suspend fun find(id: Long): Clue
}
