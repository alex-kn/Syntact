package com.alexkn.syntact.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

import com.alexkn.syntact.data.dao.base.BaseDao
import com.alexkn.syntact.data.model.SolvableItem
import com.alexkn.syntact.data.model.cto.SolvableTranslationCto

import java.time.Instant

@Dao
interface SolvableItemDao : BaseDao<SolvableItem> {

    @Query("SELECT * FROM solvableitem s JOIN clue c ON (s.id = c.clueSolvableItemId) WHERE s.nextDueDate <= :time AND s.bucketId = :bucketId ")
    fun getSolvableTranslationsDueBefore(bucketId: Long, time: Instant): LiveData<List<SolvableTranslationCto>>

    @Query("SELECT * FROM solvableitem s JOIN clue c ON (s.id = c.clueSolvableItemId) WHERE s.timesSolved > 0 AND s.bucketId = :bucketId ")
    fun getSolvableTranslations(bucketId: Long): LiveData<List<SolvableTranslationCto>>

    @Query("SELECT * FROM solvableitem s JOIN clue c ON (s.id = c.clueSolvableItemId)  WHERE s.nextDueDate <= :time AND s.bucketId = :bucketId ORDER BY s.nextDueDate LIMIT :count")
    suspend fun getNextTranslationDueBefore(bucketId: Long, time: Instant, count: Int): List<SolvableTranslationCto>

    @Query("SELECT IFNULL(MAX(id),0) FROM solvableitem WHERE bucketId = :bucketId;")
    suspend fun getMaxId(bucketId: Long): Long

    @Query("SELECT * FROM SolvableItem where id = :id")
    suspend fun find(id: Long): SolvableItem
}
