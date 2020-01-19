package com.alexkn.syntact.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alexkn.syntact.data.model.Phrase
import com.alexkn.syntact.data.model.Template

@Dao
interface TemplateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(t: Template): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(t: Collection<Template>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhrases(phrases: Collection<Phrase>)

    @Update
    suspend fun update(t: Template)

    @Delete
    suspend fun delete(t: Template)

    @Query("SELECT * FROM Template")
    fun findAll(): LiveData<List<Template>>

    @Query("SELECT * FROM Template t WHERE NOT EXISTS (SELECT * FROM Bucket b WHERE b.id = t.id)")
    fun findAvailable(): LiveData<List<Template>>

    @Query("SELECT * FROM Phrase WHERE templateId = :templateId")
    fun findPhrasesByTemplateId(templateId: Long): LiveData<List<Phrase>>

    @Query("SELECT * FROM Phrase WHERE templateId = :templateId")
    suspend fun findPhrases(templateId: Long): List<Phrase>

    @Query("DELETE FROM Template WHERE id not in (:ids)")
    suspend fun deleteTemplatesNotIn(ids: List<Long>)

}