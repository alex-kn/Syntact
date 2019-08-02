package com.alexkn.syntact.domain.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.*
import com.alexkn.syntact.app.TAG
import com.alexkn.syntact.data.dao.BucketDao
import com.alexkn.syntact.data.dao.ClueDao
import com.alexkn.syntact.data.dao.SolvableItemDao
import com.alexkn.syntact.data.model.cto.SolvableTranslationCto
import com.alexkn.syntact.domain.worker.FetchPhrasesWorker
import com.alexkn.syntact.restservice.SolvableItemService
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.roundToLong

@Singleton
class SolvableItemRepository @Inject
internal constructor(
        private val solvableItemService: SolvableItemService,
        private val clueDao: ClueDao,
        private val solvableItemDao: SolvableItemDao,
        private val bucketDao: BucketDao
) {


    fun getDueSolvableTranslations(bucketId: Long): LiveData<List<SolvableTranslationCto>> {

        return solvableItemDao.getSolvableTranslationsDueBefore(bucketId, Instant.now())
    }

    fun getSolvableTranslations(bucketId: Long): LiveData<List<SolvableTranslationCto>> {
        return solvableItemDao.getSolvableTranslations(bucketId)

    }

    suspend fun getNextSolvableTranslations(bucketId: Long?, time: Instant, count: Int): List<SolvableTranslationCto> {

        val items = solvableItemDao.getNextTranslationDueBefore(bucketId!!, time, count)

        return if (items.size == count) {
            Log.i(TAG, "All translations present")
            Log.i(TAG, items.size.toString() + " Items local")
            items
        } else {
            Log.i(TAG, "Translations missing, fetching remote")
            val bucket = bucketDao.find(bucketId)
            val maxId = solvableItemDao.getMaxId(bucketId)
            val solvableTranslationCtos = solvableItemService.fetchNewTranslations(bucket, maxId, count - items.size)
            solvableTranslationCtos.forEach { (solvableItem, clue) ->
                solvableItemDao.insert(solvableItem)
                clueDao.insert(clue)
            }
            Log.i(TAG, items.size.toString() + " Items local + " + solvableTranslationCtos.size + " Items remote")
            items + solvableTranslationCtos
        }
    }

    fun fetchSolvableItems(bucketId: Long) {

        val data = Data.Builder().putLong("bucketId", bucketId).build()
        val workRequest = OneTimeWorkRequest.Builder(FetchPhrasesWorker::class.java).setInputData(data).build()

        WorkManager.getInstance().enqueueUniqueWork(FetchPhrasesWorker::class.java.name, ExistingWorkPolicy.REPLACE, workRequest)
    }

    suspend fun solvePhrase(solvableTranslation: SolvableTranslationCto) {

        val solvableItem = solvableTranslation.solvableItem
        Log.i(TAG, "Solving Phrase " + solvableItem.text)

        val performanceRating = 3
        var easiness = solvableItem.easiness
        easiness += (-0.80 + 0.28 * performanceRating + 0.02 * performanceRating.toDouble() * performanceRating.toDouble()).toFloat()

        val consecutiveCorrectAnswers = solvableItem.consecutiveCorrectAnswers + 1

        val daysToAdd = 6 * easiness.toDouble().pow(consecutiveCorrectAnswers - 1.0)
        val nextDueDate = Instant.now().plus(daysToAdd.roundToLong(), ChronoUnit.DAYS)

        solvableItem.easiness = easiness
        solvableItem.consecutiveCorrectAnswers = consecutiveCorrectAnswers
        solvableItem.nextDueDate = nextDueDate
        solvableItem.lastSolved = Instant.now()
        solvableItem.timesSolved = solvableItem.timesSolved + 1

        solvableItemDao.update(solvableItem)
    }

    suspend fun phraseIncorrect(solvableTranslation: SolvableTranslationCto) {

        val solvableItem = solvableTranslation.solvableItem
        Log.i(TAG, "Phrase incorrect " + solvableItem.text)

        val performanceRating = 1
        var easiness = solvableItem.easiness
        easiness += (-0.80 + 0.28 * performanceRating + 0.02 * performanceRating.toDouble() * performanceRating.toDouble()).toFloat()
        easiness = max(easiness, 1.3f)

        val consecutiveCorrectAnswers = 0

        val nextDueDate = Instant.now().plus(1, ChronoUnit.DAYS)

        solvableItem.easiness = easiness
        solvableItem.consecutiveCorrectAnswers = consecutiveCorrectAnswers
        solvableItem.nextDueDate = nextDueDate

        solvableItemDao.update(solvableItem)
    }

}
