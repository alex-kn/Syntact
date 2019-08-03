package com.alexkn.syntact.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*

import com.alexkn.syntact.data.dao.base.BaseDao
import com.alexkn.syntact.data.model.Clue
import com.alexkn.syntact.data.model.SolvableItem
import com.alexkn.syntact.data.model.cto.SolvableTranslationCto

import java.time.Instant

@Dao
abstract class SolvableItemDao : BaseDao<SolvableItem> {

    @Query("SELECT * FROM solvableitem s JOIN clue c ON (s.id = c.clueSolvableItemId) WHERE (s.nextDueDate <= :time OR s.nextDueDate is null)  AND s.bucketId = :bucketId ")
    abstract fun getSolvableTranslationsDueBefore(bucketId: Long, time: Instant): LiveData<List<SolvableTranslationCto>>

    @Query("SELECT * FROM solvableitem s JOIN clue c ON (s.id = c.clueSolvableItemId) WHERE s.nextDueDate is not null AND s.bucketId = :bucketId ORDER BY s.nextDueDate")
    abstract fun getSolvableTranslations(bucketId: Long): LiveData<List<SolvableTranslationCto>>

    @Query("SELECT * FROM solvableitem s JOIN clue c ON (s.id = c.clueSolvableItemId) WHERE (s.nextDueDate <= :time OR s.nextDueDate is null)  AND s.bucketId = :bucketId ORDER BY IFNULL(s.nextDueDate,16743703664000) LIMIT :count")
    abstract suspend fun getNextTranslationDueBefore(bucketId: Long, time: Instant, count: Int): List<SolvableTranslationCto>

    @Query("SELECT IFNULL(MAX(id),0) FROM solvableitem WHERE bucketId = :bucketId;")
    abstract suspend fun getMaxId(bucketId: Long): Long

    @Query("SELECT * FROM SolvableItem where id = :id")
    abstract suspend fun find(id: Long): SolvableItem

    @Insert
    abstract suspend fun insertClue(clue: Clue)

    @Query("SELECT * FROM solvableitem s JOIN clue c ON (s.id = c.clueSolvableItemId) WHERE s.id = :id")
    abstract suspend fun getSolvableTranslation(id: Long): SolvableTranslationCto

    @Transaction
    open suspend fun insert(solvableItem: SolvableItem, clue: Clue) {
        insert(solvableItem)
        insertClue(clue)
    }


}
