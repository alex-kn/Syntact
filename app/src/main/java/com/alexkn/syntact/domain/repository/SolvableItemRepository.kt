package com.alexkn.syntact.domain.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.alexkn.syntact.data.common.AppDatabase
import com.alexkn.syntact.data.dao.BucketDao
import com.alexkn.syntact.data.dao.ClueDao
import com.alexkn.syntact.data.dao.SolvableItemDao
import com.alexkn.syntact.data.model.cto.SolvableTranslationCto
import com.alexkn.syntact.domain.worker.FetchPhrasesWorker
import com.alexkn.syntact.restservice.SolvableItemService
import io.reactivex.Maybe
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SolvableItemRepository @Inject
internal constructor(private val solvableItemService: SolvableItemService, context: Context) {

    private val clueDao: ClueDao = AppDatabase.getDatabase(context).clueDao()

    private val solvableItemDao: SolvableItemDao = AppDatabase.getDatabase(context).solvableItemDao()

    private val bucketDao: BucketDao = AppDatabase.getDatabase(context).bucketDao()

    fun getSolvableTranslations(bucketId: Long?): LiveData<List<SolvableTranslationCto>> {

        return solvableItemDao.getSolvableTranslationsDueBefore(bucketId!!, Instant.now())
    }

    fun getNextSolvableTranslations(bucketId: Long?, time: Instant, count: Int): Maybe<Array<SolvableTranslationCto>> {

        val translations = solvableItemDao.getNextTranslationDueBefore(bucketId!!, time, count)

        return translations.flatMap { items ->
            if (items.size == count) {
                Log.i(TAG, "All translations present")
                Maybe.just(items)
            } else {
                Log.i(TAG, "Translations missing, fetching remote")
                val bucket = bucketDao.find(bucketId)
                val maxId = solvableItemDao.getMaxId(bucketId)
                val solvableTranslationCtos = solvableItemService.fetchNewTranslations(bucket, maxId, count)
                solvableTranslationCtos.forEach { (solvableItem, clue) ->
                    solvableItemDao.insert(solvableItem)
                    clueDao.insert(clue)
                }
                //TODO Service to return Maybe
                Maybe.just(solvableTranslationCtos.toTypedArray())
            }
        }
    }

    fun fetchSolvableItems(bucketId: Long, startTime: Instant) {

        val data = Data.Builder().putLong("bucketId", bucketId).putLong("timestamp", startTime.toEpochMilli()).build()

        val workRequest = OneTimeWorkRequest.Builder(FetchPhrasesWorker::class.java).setInputData(data).build()
        WorkManager.getInstance().enqueueUniqueWork(FetchPhrasesWorker::class.java.name, ExistingWorkPolicy.KEEP, workRequest)
    }

    fun solvePhrase(solvableTranslation: SolvableTranslationCto) {

        val solvableItem = solvableTranslation.solvableItem
        Log.i(TAG, "Solving Phrase " + solvableItem.text)

        val performanceRating = 3
        var easiness = solvableItem.easiness
        easiness += (-0.80 + 0.28 * performanceRating + 0.02 * performanceRating.toDouble() * performanceRating.toDouble()).toFloat()

        val consecutiveCorrectAnswers = solvableItem.consecutiveCorrectAnswers + 1

        val daysToAdd = 6 * Math.pow(easiness.toDouble(), consecutiveCorrectAnswers - 1.0)
        val nextDueDate = Instant.now().plus(Math.round(daysToAdd), ChronoUnit.DAYS)

        solvableItem.easiness = easiness
        solvableItem.consecutiveCorrectAnswers = consecutiveCorrectAnswers
        solvableItem.nextDueDate = nextDueDate
        solvableItem.lastSolved = Instant.now()
        solvableItem.timesSolved = solvableItem.timesSolved + 1

        solvableItemDao.update(solvableItem)
    }

    companion object {

        private val TAG = SolvableItemRepository::class.java.simpleName
    }
}
