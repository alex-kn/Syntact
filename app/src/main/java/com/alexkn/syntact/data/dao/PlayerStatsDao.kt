package com.alexkn.syntact.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.alexkn.syntact.data.dao.base.BaseDao
import com.alexkn.syntact.data.model.Clue
import com.alexkn.syntact.data.model.views.PlayerStats

@Dao
interface PlayerStatsDao {

    @Query("SELECT * FROM PlayerStats")
    fun find(): LiveData<PlayerStats>
}
