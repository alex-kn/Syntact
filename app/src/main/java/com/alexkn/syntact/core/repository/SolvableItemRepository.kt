package com.alexkn.syntact.core.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.alexkn.syntact.app.TAG
import com.alexkn.syntact.data.dao.SolvableItemDao
import com.alexkn.syntact.data.model.SolvableTranslationCto
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.roundToLong


@Singleton
class SolvableItemRepository @Inject constructor(
        private val solvableItemDao: SolvableItemDao
) {

    private val maxPerformanceRating = 5.0

    fun getSolvableTranslations(bucketId: Long): LiveData<List<SolvableTranslationCto>> = solvableItemDao.getSolvableTranslations(bucketId)

    suspend fun findNextSolvableTranslation(bucketId: Long, time: Instant): SolvableTranslationCto? {
        return solvableItemDao.getNextTranslationDueBefore(bucketId, time)
    }

    suspend fun disableSolvableItem(solvableTranslationCto: SolvableTranslationCto) {

        val solvableItem = solvableTranslationCto.solvableItem
        solvableItem.disabled = true
        solvableItemDao.update(solvableItem)
    }

    suspend fun markPhraseCorrect(solvableTranslation: SolvableTranslationCto, scoreRatio: Double) {

        val solvableItem = solvableTranslation.solvableItem

        Log.d(TAG, "markPhraseCorrect: $solvableItem (Before)")

        val performanceRating = maxPerformanceRating * scoreRatio
        var easiness = solvableItem.easiness
        easiness += (-0.80 + 0.28 * performanceRating + 0.02 * performanceRating * performanceRating).toFloat()

        val daysToAdd = 6 * easiness.toDouble().pow(solvableItem.consecutiveCorrectAnswers - 1.0)
        val consecutiveCorrectAnswers = solvableItem.consecutiveCorrectAnswers + 1

        val nextDueDate = Instant.now().plus(daysToAdd.roundToLong(), ChronoUnit.DAYS)

        solvableItem.easiness = easiness
        solvableItem.consecutiveCorrectAnswers = consecutiveCorrectAnswers
        solvableItem.nextDueDate = nextDueDate
        solvableItem.lastSolved = Instant.now()
        solvableItem.timesSolved = solvableItem.timesSolved + 1

        solvableItemDao.update(solvableItem)
        Log.d(TAG, "markPhraseCorrect: $solvableItem (Updated)")
    }

    suspend fun markPhraseIncorrect(solvableTranslation: SolvableTranslationCto, scoreRatio: Double) {

        val solvableItem = solvableTranslation.solvableItem

        Log.d(TAG, "markPhraseIncorrect: $solvableItem (Before)")

        val performanceRating = maxPerformanceRating * scoreRatio
        var easiness = solvableItem.easiness
        easiness += (-0.80 + 0.28 * performanceRating + 0.02 * performanceRating * performanceRating).toFloat()
        easiness = max(easiness, 1.3f)

        val consecutiveCorrectAnswers = 0

        val nextDueDate = Instant.now().plus(1, ChronoUnit.DAYS)

        solvableItem.easiness = easiness
        solvableItem.consecutiveCorrectAnswers = consecutiveCorrectAnswers
        solvableItem.nextDueDate = nextDueDate

        solvableItemDao.update(solvableItem)
        Log.d(TAG, "markPhraseIncorrect: $solvableItem (Updated)")
    }

}
