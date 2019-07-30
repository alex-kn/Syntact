package com.alexkn.syntact.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

import com.alexkn.syntact.data.dao.base.BaseDao
import com.alexkn.syntact.data.model.SolvableItem
import com.alexkn.syntact.data.model.cto.SolvableTranslationCto

import java.time.Instant

import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface SolvableItemDao : BaseDao<SolvableItem> {

    @Query("SELECT * FROM solvableitem s JOIN clue c ON (s.id = c.clueSolvableItemId) WHERE s.nextDueDate <= :time AND s.bucketId = :bucketId ")
    fun getSolvableTranslationsDueBefore(bucketId: Long, time: Instant): LiveData<List<SolvableTranslationCto>>

    @Query("SELECT * FROM solvableitem s JOIN clue c ON (s.id = c.clueSolvableItemId) WHERE s.bucketId = :bucketId ")
    fun getSolvableTranslations(bucketId: Long): LiveData<List<SolvableTranslationCto>>

    @Query("SELECT * FROM solvableitem s JOIN clue c ON (s.id = c.clueSolvableItemId)  WHERE s.nextDueDate <= :time AND s.bucketId = :bucketId ORDER BY s.nextDueDate LIMIT :count")
    fun getNextTranslationDueBefore(bucketId: Long, time: Instant, count: Int): Single<List<SolvableTranslationCto>>

    @Query("SELECT MAX(id) FROM solvableitem WHERE bucketId = :bucketId;")
    fun getMaxId(bucketId: Long): Long

    @Query("SELECT * FROM SolvableItem where id = :id")
    fun find(id: Long): SolvableItem
}
