package com.alexkn.syntact.domain.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.*
import com.alexkn.syntact.app.Property
import com.alexkn.syntact.app.TAG
import com.alexkn.syntact.data.dao.ClueDao
import com.alexkn.syntact.data.dao.SolvableItemDao
import com.alexkn.syntact.data.model.Clue
import com.alexkn.syntact.data.model.cto.SolvableTranslationCto
import com.alexkn.syntact.domain.worker.FetchPhrasesWorker
import com.alexkn.syntact.rest.service.SyntactService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.roundToLong

@Singleton
class SolvableItemRepository @Inject
internal constructor(
        private val property: Property,
        private val syntactService: SyntactService,
        private val solvableItemDao: SolvableItemDao,
        private val clueDao: ClueDao
) {

    fun getSolvableTranslations(bucketId: Long): LiveData<List<SolvableTranslationCto>> = solvableItemDao.getSolvableTranslations(bucketId)

    suspend fun findNextSolvableTranslation(bucketId: Long, time: Instant): SolvableTranslationCto {

        val nextTranslation = solvableItemDao.getNextTranslationDueBefore(bucketId, time)

        return if (nextTranslation.clue == null) {
            val token = "Token " + property["api-auth-token"]
            val translations = syntactService.getTranslations(token, nextTranslation.solvableItem.translationUrl, Locale.getDefault().language)
            if (translations.size > 1) {
                Log.i(TAG, "Multiple Translations not yet supported, using first translation and discarding others")
            }
            if (translations.isEmpty()) {
                throw Exception("No TranslationResponse found for ${nextTranslation.solvableItem}")
            }
            val translation = translations[0]
            val clue = Clue(
                    id = translation.id,
                    text = translation.text.capitalize(),
                    solvableItemId = nextTranslation.solvableItem.id
            )
            clueDao.insert(clue)
            Log.i(TAG, "Saved Clue $clue")
            solvableItemDao.getNextTranslationDueBefore(bucketId, time)
        } else {
            nextTranslation
        }

    }

    fun fetchSolvableItems(bucketId: Long, count: Int? = null) {

        val data = Data.Builder().putLong("bucketId", bucketId)
        count?.let { data.putInt("count", count) }
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workRequest = OneTimeWorkRequest.Builder(FetchPhrasesWorker::class.java).setInputData(data.build()).setConstraints(constraints).build()
        WorkManager.getInstance().enqueueUniqueWork(FetchPhrasesWorker::class.java.name, ExistingWorkPolicy.KEEP, workRequest)
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
