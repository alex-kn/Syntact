package com.alexkn.syntact.domain.repository

import androidx.lifecycle.LiveData
import androidx.work.*
import com.alexkn.syntact.app.Property
import com.alexkn.syntact.data.common.AppDatabase
import com.alexkn.syntact.data.dao.BucketDao
import com.alexkn.syntact.data.model.Bucket
import com.alexkn.syntact.data.model.views.BucketDetail
import com.alexkn.syntact.domain.worker.CreateBucketWorker
import com.alexkn.syntact.restservice.SyntactService
import com.alexkn.syntact.restservice.Template
import com.google.common.util.concurrent.ListenableFuture
import io.reactivex.Single
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BucketRepository @Inject
internal constructor(
        private val syntactService: SyntactService,
        private val property: Property,
        private val bucketDao: BucketDao
) {

    val availableLanguages: MutableList<Locale> by lazy {
        property["available-languages"].split(",").map { Locale(it) }.toMutableList()
    }

    val bucketDetails: LiveData<List<BucketDetail>>
        get() = bucketDao.findBucketDetails()

    fun findAvailableTemplates(): Single<List<Template>> {

        val token = "Token " + property["api-auth-token"]
        return syntactService.getTemplates(token)
    }

    fun addBucketWithNewTemplate(name: String, language: Locale, words: List<String>): ListenableFuture<Operation.State.SUCCESS> {

        val data = Data.Builder().putString("name", name).putString("language", language.language)
                .putStringArray("words", words.toTypedArray()).build()

        val workRequest = OneTimeWorkRequest.Builder(CreateBucketWorker::class.java).setInputData(data).build()
        return WorkManager.getInstance().enqueueUniqueWork(CreateBucketWorker::class.java.name, ExistingWorkPolicy.KEEP, workRequest).result
    }

    fun addBucketWithExistingTemplate(template: Template) {

        val sourceLanguage = Locale.getDefault()

        val bucket = Bucket(language = template.language, userLanguage = sourceLanguage, phrasesUrl = template.phrasesUrl, itemCount = template.count)
        bucketDao.insert(bucket)
    }

    fun removeLanguage(bucket: Bucket) {

        bucketDao.delete(bucket)
    }

    fun deleteBucket(id: Long) {
        bucketDao.delete(id)
    }

    fun getBucket(id: Long?): LiveData<Bucket> {

        return bucketDao.findBucket(id!!)
    }
}
