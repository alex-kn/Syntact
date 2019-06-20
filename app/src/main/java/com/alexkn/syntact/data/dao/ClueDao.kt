package com.alexkn.syntact.data.dao

import androidx.room.Dao
import androidx.room.Query

import com.alexkn.syntact.data.dao.base.BaseDao
import com.alexkn.syntact.data.model.Clue

@Dao
interface ClueDao : BaseDao<Clue> {

    @Query("SELECT * FROM Clue where clueId = :id")
    fun find(id: Long): Clue
}
