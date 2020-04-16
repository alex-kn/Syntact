package com.alexkn.syntact.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alexkn.syntact.core.model.Clue
import com.alexkn.syntact.core.model.SolvableItem
import com.alexkn.syntact.core.model.SolvableTranslationCto
import com.alexkn.syntact.data.dao.base.BaseDao
import java.time.Instant

@Dao
abstract class SolvableItemDao : BaseDao<SolvableItem> {

    @Query("SELECT * FROM solvableitem s LEFT JOIN clue c ON s.id = c.clueSolvableItemId WHERE s.nextDueDate <= :time AND s.deckId = :deckId")
    abstract suspend fun findSolvedItemsDueBefore(deckId: Long, time: Instant): List<SolvableTranslationCto>

    @Query("select * from SolvableItem s left join Clue C on s.id = C.clueSolvableItemId where s.nextDueDate is null and s.deckId = :deckId limit :limit")
    abstract suspend fun findUnsolvedItems(deckId: Long, limit: Int): List<SolvableTranslationCto>

    @Query("select * from SolvableItem s left join Clue C on s.id = C.clueSolvableItemId where s.lastSolved >= :from and s.lastSolved < :to and s.deckId = :deckId")
    abstract suspend fun findItemsSolvedBetween(deckId: Long, from: Instant, to: Instant): List<SolvableTranslationCto>

    @Query("select * from SolvableItem s left join Clue C on s.id = C.clueSolvableItemId where s.lastAttempt >= :from and s.lastAttempt < :to and s.deckId = :deckId")
    abstract suspend fun findItemsAttemptedBetween(deckId: Long, from: Instant, to: Instant): List<SolvableTranslationCto>

    @Query("select count(*) from SolvableItem s where s.firstSeen >= :from and s.firstSeen < :to and s.deckId = :deckId")
    abstract suspend fun findItemsFirstSeenBetweenCount(deckId: Long, from: Instant, to: Instant): Int

    @Query("SELECT * FROM solvableitem s LEFT JOIN clue c ON (s.id = c.clueSolvableItemId) WHERE s.deckId = :deckId")
    abstract fun getSolvableTranslations(deckId: Long): LiveData<List<SolvableTranslationCto>>

    @Query("SELECT * FROM solvableitem s LEFT JOIN clue c ON (s.id = c.clueSolvableItemId) WHERE (s.nextDueDate <= :time OR s.nextDueDate is null)  AND s.deckId = :bucketId ORDER BY IFNULL(s.nextDueDate,16743703664000) LIMIT 1")
    abstract suspend fun getNextTranslationDueBefore(bucketId: Long, time: Instant): SolvableTranslationCto?

    @Query("SELECT IFNULL(MAX(id),0) FROM solvableitem WHERE deckId = :deckId;")
    abstract suspend fun getMaxId(deckId: Long): Long

    @Query("SELECT * FROM SolvableItem where id = :id")
    abstract suspend fun find(id: Long): SolvableItem

    @Insert
    abstract suspend fun insertClue(clue: Clue)

    @Query("SELECT * FROM solvableitem s LEFT JOIN clue c ON (s.id = c.clueSolvableItemId) WHERE s.id = :id")
    abstract suspend fun getSolvableTranslation(id: Long): SolvableTranslationCto

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
