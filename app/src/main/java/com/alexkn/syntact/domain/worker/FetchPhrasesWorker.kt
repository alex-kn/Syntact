package com.alexkn.syntact.domain.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.alexkn.syntact.app.ApplicationComponentProvider
import com.alexkn.syntact.app.Property
import com.alexkn.syntact.app.TAG
import com.alexkn.syntact.data.dao.BucketDao
import com.alexkn.syntact.data.dao.ClueDao
import com.alexkn.syntact.data.dao.SolvableItemDao
import com.alexkn.syntact.data.model.Clue
import com.alexkn.syntact.data.model.SolvableItem
import com.alexkn.syntact.restservice.SolvableItemService
import com.alexkn.syntact.restservice.SyntactService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchPhrasesWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {


    @Inject
    lateinit var solvableItemService: SolvableItemService

    @Inject
    lateinit var syntactService: SyntactService

    @Inject
    lateinit var property: Property

    @Inject
    lateinit var clueDao: ClueDao

    @Inject
    lateinit var solvableItemDao: SolvableItemDao

    @Inject
    lateinit var bucketDao: BucketDao

    init {
        (context as ApplicationComponentProvider).applicationComponent.inject(this)
    }


    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        val bucketId = inputData.getLong("bucketId", -1L)
        if (bucketId == -1L) {
            Result.failure()
        }

        val token = "Token " + property["api-auth-token"]
        val bucket = bucketDao.find(bucketId)
        val maxId = solvableItemDao.getMaxId(bucketId)

        val phrases = syntactService.getPhrases(token, bucket.phrasesUrl, maxId, bucket.itemCount)
        phrases.forEach { phrase ->
            val solvableItem = SolvableItem(
                    id = phrase.id,
                    text = phrase.text.capitalize(),
                    bucketId = bucket.id
            )
            Log.i(TAG, "Fetched Phrase " + phrase.text + ". Fetching translation...")

            val translations = syntactService.getTranslations(token, phrase.translationsUrl, bucket.userLanguage.language)
            if (translations.size > 1) {
                Log.i(TAG, "Multiple Translations not yet supported, using first translation and discarding others")
            }
            if (translations.isEmpty()) {
                throw NoSuchElementException("No Translation found for $phrase in $bucket")
            }
            val translation = translations[0]
            val clue = Clue(
                    id = translation.id,
                    text = translation.text.capitalize(),
                    solvableItemId = phrase.id
            )
            solvableItemDao.insert(solvableItem)
            clueDao.insert(clue)

        }

        Result.success()
    }

}
