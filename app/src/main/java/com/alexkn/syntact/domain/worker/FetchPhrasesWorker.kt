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
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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

    override suspend fun doWork(): Result = coroutineScope {

        val bucketId = inputData.getLong("bucketId", -1L)
        if (bucketId == -1L) {
            Result.failure()
        }


        val token = "Token " + property["api-auth-token"]
        val bucket = bucketDao.find(bucketId)

        val phrases = syntactService.getPhrases(token, bucket.phrasesUrl, 0, bucket.itemCount)
        for (phrase in phrases){
            async {

                if (solvableItemDao.find(phrase.id) == null) {

                    val solvableItem = SolvableItem(
                            id = phrase.id,
                            text = phrase.text.capitalize(),
                            bucketId = bucket.id
                    )

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

                    solvableItemDao.insert(solvableItem, clue)
                    Log.i(TAG, "Saved Phrase " + phrase.text + " and Translation " + clue.text)
                } else {
                    Log.i(TAG, "Phrase " + phrase.text + " already on device")
                }
            }
        }


        Result.success()
    }


}
