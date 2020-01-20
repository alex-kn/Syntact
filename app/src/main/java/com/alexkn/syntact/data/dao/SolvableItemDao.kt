package com.alexkn.syntact.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*

import com.alexkn.syntact.data.dao.base.BaseDao
import com.alexkn.syntact.data.model.Clue
import com.alexkn.syntact.data.model.SolvableItem
import com.alexkn.syntact.data.model.SolvableTranslationCto

import java.time.Instant

@Dao
abstract class SolvableItemDao : BaseDao<SolvableItem> {

    @Query("SELECT * FROM solvableitem s LEFT JOIN clue c ON (s.id = c.clueSolvableItemId) WHERE (s.nextDueDate <= :time OR s.nextDueDate is null)  AND s.deckId = :bucketId AND s.disabled  = 0 ")
    abstract fun getSolvableTranslationsDueBefore(bucketId: Long, time: Instant): LiveData<List<SolvableTranslationCto>>

    @Query("SELECT * FROM solvableitem s LEFT JOIN clue c ON (s.id = c.clueSolvableItemId) WHERE s.deckId = :deckId AND s.disabled  = 0")
    abstract fun getSolvableTranslations(deckId: Long): LiveData<List<SolvableTranslationCto>>

    @Query("SELECT * FROM solvableitem s LEFT JOIN clue c ON (s.id = c.clueSolvableItemId) WHERE (s.nextDueDate <= :time OR s.nextDueDate is null)  AND s.deckId = :deckId AND s.disabled  = 0 ORDER BY IFNULL(s.nextDueDate,16743703664000) LIMIT :count")
    abstract suspend fun getNextTranslationsDueBefore(deckId: Long, time: Instant, count: Int): List<SolvableTranslationCto>

    @Query("SELECT * FROM solvableitem s LEFT JOIN clue c ON (s.id = c.clueSolvableItemId) WHERE (s.nextDueDate <= :time OR s.nextDueDate is null)  AND s.deckId = :bucketId AND s.disabled  = 0 ORDER BY IFNULL(s.nextDueDate,16743703664000) LIMIT 1")
    abstract suspend fun getNextTranslationDueBefore(bucketId: Long, time: Instant): SolvableTranslationCto?

    @Query("SELECT IFNULL(MAX(id),0) FROM solvableitem WHERE deckId = :deckId;")
    abstract suspend fun getMaxId(deckId: Long): Long

    @Query("SELECT * FROM SolvableItem where id = :id")
    abstract suspend fun find(id: Long): SolvableItem

    @Insert
    abstract suspend fun insertClue(clue: Clue)

    @Query("SELECT * FROM solvableitem s LEFT JOIN clue c ON (s.id = c.clueSolvableItemId) WHERE s.id = :id")
    abstract suspend fun getSolvableTranslation(id: Long): SolvableTranslationCto

    @Query("SELECT s.* FROM solvableitem s LEFT JOIN clue c ON (s.id = c.clueSolvableItemId) WHERE s.deckId = :deckId AND c.clueId  is null")
    abstract suspend fun findSolvableItemsWithoutTranslation(deckId: Long): List<SolvableItem>

    @Delete
    abstract suspend fun deleteClue(clue: Clue)

    @Transaction
    open suspend fun insert(solvableItem: SolvableItem, clue: Clue) {
        insert(solvableItem)
        insertClue(clue)
    }

    @Transaction
    open suspend fun delete(solvableItem: SolvableItem, clue: Clue) {
        delete(solvableItem)
        deleteClue(clue)
    }

}
