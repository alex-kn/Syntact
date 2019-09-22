package com.alexkn.syntact.domain.repository

import androidx.lifecycle.LiveData
import androidx.work.*
import com.alexkn.syntact.app.Property
import com.alexkn.syntact.data.dao.BucketDao
import com.alexkn.syntact.data.dao.PlayerStatsDao
import com.alexkn.syntact.data.dao.SolvableItemDao
import com.alexkn.syntact.data.model.Bucket
import com.alexkn.syntact.data.model.SolvableItem
import com.alexkn.syntact.data.model.Template
import com.alexkn.syntact.data.model.views.BucketDetail
import com.alexkn.syntact.data.model.views.PlayerStats
import com.alexkn.syntact.domain.worker.CreateBucketWorker
import com.alexkn.syntact.rest.service.SyntactService
import com.alexkn.syntact.rest.to.TemplateResponse
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BucketRepository @Inject
internal constructor(
        private val syntactService: SyntactService,
        private val property: Property,
        private val bucketDao: BucketDao,
        private val playerStatsDao: PlayerStatsDao,
        private val solvableItemDao: SolvableItemDao,
        private val solvableItemRepository: SolvableItemRepository
) {

    val availableLanguages: MutableList<Locale> = property["available-languages"].split(",").map { Locale(it) }.toMutableList()


    val bucketDetails: LiveData<List<BucketDetail>>
        get() = bucketDao.findBucketDetails()

    suspend fun findAvailableTemplates(): List<TemplateResponse> {

        val token = "Token " + property["api-auth-token"]
        return syntactService.getTemplates(token)
    }

    fun addBucketWithNewTemplate(name: String, language: Locale, words: List<String>): ListenableFuture<Operation.State.SUCCESS> {

        val data = Data.Builder().putString("name", name).putString("language", language.language)
                .putStringArray("words", words.toTypedArray()).build()

        val workRequest = OneTimeWorkRequest.Builder(CreateBucketWorker::class.java).setInputData(data).build()
        return WorkManager.getInstance().enqueueUniqueWork(CreateBucketWorker::class.java.name, ExistingWorkPolicy.KEEP, workRequest).result
    }

    suspend fun addBucketWithExistingTemplate(template: Template)  = withContext(Dispatchers.Default) {

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

    suspend fun removeLanguage(bucket: Bucket) {

        bucketDao.delete(bucket)
    }

    suspend fun deleteBucket(id: Long) {
        bucketDao.delete(id)
    }

    fun getBucket(id: Long): LiveData<Bucket> {

        return bucketDao.findBucket(id)
    }

    fun getBucketDetail(id: Long): LiveData<BucketDetail> {
        return bucketDao.findBucketDetail(id)
    }

    fun getPlayerStats(): LiveData<PlayerStats> {
        return playerStatsDao.find()
    }
}
