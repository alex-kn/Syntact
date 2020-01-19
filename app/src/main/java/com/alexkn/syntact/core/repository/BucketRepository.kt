package com.alexkn.syntact.core.repository

import androidx.lifecycle.LiveData
import com.alexkn.syntact.app.Property
import com.alexkn.syntact.data.dao.BucketDao
import com.alexkn.syntact.data.dao.PlayerStatsDao
import com.alexkn.syntact.data.dao.SolvableItemDao
import com.alexkn.syntact.data.model.*
import com.alexkn.syntact.service.SyntactService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BucketRepository @Inject constructor(
        private val syntactService: SyntactService,
        private val property: Property,
        private val bucketDao: BucketDao,
        private val playerStatsDao: PlayerStatsDao,
        private val solvableItemDao: SolvableItemDao
) {

    val availableLanguages: MutableList<Locale> = property["available-languages"].split(",").map { Locale(it) }.toMutableList()

    val bucketDetails: LiveData<List<BucketDetail>>
        get() = bucketDao.findBucketDetails()

    suspend fun addBucketWithExistingTemplate(template: Template) = withContext(Dispatchers.Default) {

        val sourceLanguage = Locale.getDefault()

        val bucket = Bucket(
                id = template.id,
                name = template.name,
                language = template.language,
                userLanguage = sourceLanguage,
                phrasesUrl = template.phrasesUrl,
                itemCount = template.count
        )
        val id = bucketDao.insert(bucket)
        val token = "Token " + property["api-auth-token"]

        val phrases = syntactService.getPhrases(token, bucket.phrasesUrl)

        val solvableItems = phrases.map {
            SolvableItem(
                    id = it.id,
                    text = it.text.capitalize(),
                    bucketId = id,
                    translationUrl = it.translationsUrl
            )
        }

        solvableItemDao.insert(solvableItems)
    }

    suspend fun deleteBucket(id: Long) {
        bucketDao.delete(id)
    }

    fun getBucketDetail(id: Long): LiveData<BucketDetail> {
        return bucketDao.findBucketDetail(id)
    }

    fun getPlayerStats(): LiveData<PlayerStats> {
        return playerStatsDao.find()
    }
}
