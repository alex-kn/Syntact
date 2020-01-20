package com.alexkn.syntact.core.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.alexkn.syntact.app.Property
import com.alexkn.syntact.app.TAG
import com.alexkn.syntact.data.dao.ClueDao
import com.alexkn.syntact.data.dao.SolvableItemDao
import com.alexkn.syntact.data.model.Clue
import com.alexkn.syntact.data.model.SolvableTranslationCto
import com.alexkn.syntact.service.SyntactService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.roundToLong

@Singleton
class SolvableItemRepository @Inject constructor(
        private val solvableItemDao: SolvableItemDao
) {

    fun getSolvableTranslations(bucketId: Long): LiveData<List<SolvableTranslationCto>> = solvableItemDao.getSolvableTranslations(bucketId)

    suspend fun findNextSolvableTranslation(bucketId: Long, time: Instant): SolvableTranslationCto? {
        return solvableItemDao.getNextTranslationDueBefore(bucketId, time)
    }

    suspend fun disableSolvableItem(solvableTranslationCto: SolvableTranslationCto) {

        val solvableItem = solvableTranslationCto.solvableItem
        solvableItem.disabled = true;
        solvableItemDao.update(solvableItem)
    }

    suspend fun solvePhrase(solvableTranslation: SolvableTranslationCto) {

        val solvableItem = solvableTranslation.solvableItem
        Log.i(TAG, "Solving PhraseResponse " + solvableItem.text)

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
        Log.i(TAG, "PhraseResponse incorrect " + solvableItem.text)

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
