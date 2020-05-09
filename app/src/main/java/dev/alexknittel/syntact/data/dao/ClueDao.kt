package dev.alexknittel.syntact.data.dao

import androidx.room.Dao
import androidx.room.Query
import dev.alexknittel.syntact.core.model.Clue
import dev.alexknittel.syntact.data.dao.base.BaseDao

@Dao
interface ClueDao : BaseDao<Clue> {

    @Query("SELECT * FROM Clue where clueId = :id")
    suspend fun find(id: Long): Clue
}
