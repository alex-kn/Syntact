package com.alexkn.syntact.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.alexkn.syntact.data.dao.base.BaseDao
import com.alexkn.syntact.data.model.*

@Dao
abstract class DeckDao : BaseDao<Deck> {


    @Query("DELETE FROM Deck where id = :id")
    abstract suspend fun delete(id: Long)

    @Query("SELECT * FROM Deck where id = :id")
    abstract suspend fun find(id: Long): Deck

    @Query("SELECT * FROM Deck")
    abstract fun findAllLiveData(): LiveData<List<Deck>>

    @Query("SELECT * FROM Deck")
    abstract fun findAll(): List<Deck>

    @Query("SELECT * FROM Deck WHERE id = :id LIMIT 1")
    abstract fun findLive(id: Long): LiveData<Deck?>

    @Query("SELECT * FROM DeckDetail")
    abstract fun findBucketDetails(): LiveData<List<DeckDetail>>

    @Query("SELECT * FROM DeckDetail WHERE id = :id LIMIT 1")
    abstract fun findBucketDetail(id: Long): LiveData<DeckDetail>

    @Transaction
    open suspend fun insert(deck: Deck, solvableTranslations: List<SolvableTranslationCto>) {
        val deckId = insert(deck)
        solvableTranslations.forEach {
            val solvableItem = it.solvableItem
            solvableItem.deckId = deckId
            val solvableItemId = insertSolvableItem(solvableItem)
            val clue = it.clue
            clue.solvableItemId = solvableItemId
            insertClue(clue)
        }
    }

    @Insert
    abstract suspend fun insertSolvableItem(solvableItem: SolvableItem):Long

    @Insert
    abstract suspend fun insertClue(clue: Clue): Long

}
