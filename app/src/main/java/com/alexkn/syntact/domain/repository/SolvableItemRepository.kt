package com.alexkn.syntact.domain.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.alexkn.syntact.app.TAG
import com.alexkn.syntact.data.common.AppDatabase
import com.alexkn.syntact.data.dao.BucketDao
import com.alexkn.syntact.data.dao.ClueDao
import com.alexkn.syntact.data.dao.SolvableItemDao
import com.alexkn.syntact.data.model.cto.SolvableTranslationCto
import com.alexkn.syntact.domain.worker.FetchPhrasesWorker
import com.alexkn.syntact.restservice.SolvableItemService
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SolvableItemRepository @Inject
internal constructor(
        private val solvableItemService: SolvableItemService,
        appDatabase: AppDatabase
) {

    private val clueDao: ClueDao = appDatabase.clueDao()

    private val solvableItemDao: SolvableItemDao = appDatabase.solvableItemDao()

    private val bucketDao: BucketDao = appDatabase.bucketDao()

    fun getSolvableTranslations(bucketId: Long?): LiveData<List<SolvableTranslationCto>> {

        return solvableItemDao.getSolvableTranslationsDueBefore(bucketId!!, Instant.now())
    }

    fun getNextSolvableTranslations(bucketId: Long?, time: Instant, count: Int): Single<List<SolvableTranslationCto>> {

        val translations = solvableItemDao.getNextTranslationDueBefore(bucketId!!, time, count)

        return translations.map { items ->
            if (items.size == count) {
                Log.i(TAG, "All translations present")
                Log.i(TAG, items.size.toString() + " Items local")
                items
            } else {
                Log.i(TAG, "Translations missing, fetching remote")
                val bucket = bucketDao.find(bucketId)
                val maxId = solvableItemDao.getMaxId(bucketId)
                val solvableTranslationCtos = solvableItemService.fetchNewTranslations(bucket, maxId, count - items.size).blockingGet()
                solvableTranslationCtos.forEach { (solvableItem, clue) ->
                    solvableItemDao.insert(solvableItem)
                    clueDao.insert(clue)
                }
                Log.i(TAG, items.size.toString() + " Items local + " + solvableTranslationCtos.size + " Items remote")
                items + solvableTranslationCtos
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
