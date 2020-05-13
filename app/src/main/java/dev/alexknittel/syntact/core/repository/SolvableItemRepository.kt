package dev.alexknittel.syntact.core.repository

import android.util.Log
import androidx.lifecycle.LiveData
import dev.alexknittel.syntact.app.TAG
import dev.alexknittel.syntact.core.common.util.atEndOfDay
import dev.alexknittel.syntact.core.common.util.atStartOfDay
import dev.alexknittel.syntact.core.model.SolvableTranslationCto
import dev.alexknittel.syntact.data.dao.SolvableItemDao
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*
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

    suspend fun findNearestDueDate(userLang: Locale) = solvableItemDao.findNearestDueDate(userLang)

    suspend fun findNextSolvableItem(deckId: Long, time: Instant, newItemsPerDay: Int): SolvableTranslationCto? {

        val newItems = findNewItemsForToday(deckId, newItemsPerDay)
        if (newItems.isNotEmpty()) return newItems[0]
        val reviewItems = findItemsDueForReview(deckId, time)
        return if (reviewItems.isNotEmpty()) reviewItems[0] else null
    }

    suspend fun findItemsDueForReview(deckId: Long, time: Instant): List<SolvableTranslationCto> {

        val endOfDay = time.atZone(ZoneId.systemDefault()).withHour(23).withMinute(59).withSecond(59).toInstant()
        return solvableItemDao.findSolvedItemsDueBefore(deckId, endOfDay)
    }

    suspend fun findNewItemsForToday(deckId: Long, limit: Int): List<SolvableTranslationCto> {
        val firstSeenTodayCount = solvableItemDao.findItemsFirstSeenBetweenCount(deckId, Instant.now().atStartOfDay(), Instant.now())
        return solvableItemDao.findUnsolvedItems(deckId, limit - firstSeenTodayCount)
    }

    suspend fun findItemsSolvedOnDay(deckId: Long, time: Instant): List<SolvableTranslationCto> {
        return solvableItemDao.findItemsSolvedBetween(deckId, time.atStartOfDay(), time.atEndOfDay())
    }

    suspend fun findItemsAttemptedOnDay(deckId: Long, time: Instant): List<SolvableTranslationCto> {
        return solvableItemDao.findItemsAttemptedBetween(deckId, time.atStartOfDay(), time.atEndOfDay())
    }

    suspend fun deleteSolvableTranslationCto(solvableTranslation: SolvableTranslationCto) {
        solvableItemDao.delete(solvableTranslation.solvableItem, solvableTranslation.clue)
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
        if (solvableItem.lastAttempt == null) solvableItem.firstSeen = Instant.now()
        solvableItem.lastAttempt = Instant.now()
        solvableItem.timesSolved += 1


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
        if (solvableItem.lastAttempt == null) solvableItem.firstSeen = Instant.now()
        solvableItem.nextDueDate = nextDueDate
        solvableItem.lastFailed = Instant.now()
        solvableItem.lastAttempt = Instant.now()

        solvableItemDao.update(solvableItem)
        Log.d(TAG, "markPhraseIncorrect: $solvableItem (Updated)")
    }

}
