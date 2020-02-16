package com.alexkn.syntact.presentation.deckboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexkn.syntact.core.repository.DeckRepository
import com.alexkn.syntact.core.repository.SolvableItemRepository
import com.alexkn.syntact.data.model.DeckDetail
import com.alexkn.syntact.data.model.SolvableTranslationCto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.commons.text.similarity.LevenshteinDistance
import java.time.Instant
import javax.inject.Inject


class DeckBoardViewModel @Inject constructor(
        private val solvableItemRepository: SolvableItemRepository,
        private val deckRepository: DeckRepository
) : ViewModel() {

    lateinit var deck: LiveData<DeckDetail>
        private set

    var translation: MutableLiveData<SolvableTranslationCto?> = MutableLiveData()
        private set

    lateinit var items: List<SolvableTranslationCto>

    private val levenshteinDistance: LevenshteinDistance = LevenshteinDistance.getDefaultInstance()
    private val _maxScore: MutableLiveData<Int> = MutableLiveData(0)
    private val _currentScore: MutableLiveData<Int> = MutableLiveData(0)

    val maxScore: LiveData<Int>
        get() = _maxScore

    val currentScore: LiveData<Int>
        get() = _currentScore

    var done = false

    private var bucketId: Long? = null

    val scoreRatio: Double
        get() = currentScore.value!!.toDouble() / maxScore.value!!


    fun init(bucketId: Long) {
        this.bucketId = bucketId
        deck = deckRepository.getBucketDetail(bucketId)
        fetchNext()
    }

    fun fetchNext() {
        viewModelScope.launch(Dispatchers.Default) {
            val nextTranslation = solvableItemRepository.findNextSolvableItem(bucketId!!, Instant.now())
            translation.postValue(nextTranslation)
            _maxScore.postValue(nextTranslation?.solvableItem?.text?.length ?: 0)
            _currentScore.postValue(0)
        }
    }

    fun checkSolution(solution: String, peek: Boolean = false): Double {
        val translationCto = translation.value
        return if (translationCto == null) {
            0.0
        } else {
            _currentScore.value = getCurrentScore(translationCto, solution)
            _maxScore.value = translationCto.solvableItem.text.length
            if (isAttemptCorrect(solution)) {
                viewModelScope.launch(Dispatchers.Default) { solvableItemRepository.markPhraseCorrect(translationCto, scoreRatio) }
            } else {
                if (!peek) viewModelScope.launch(Dispatchers.Default) { solvableItemRepository.markPhraseIncorrect(translationCto, scoreRatio) }
            }
            scoreRatio
        }
    }

    private fun getCurrentScore(translationCto: SolvableTranslationCto, solution: String): Int {
        val value = translationCto.solvableItem.text.length - levenshteinDistance.apply(translationCto.solvableItem.text.toLowerCase(), solution.toLowerCase())
        return if (value >= 0) value else 0
    }

    private fun isAttemptCorrect(attempt: String): Boolean {
        return attempt.equals(translation.value?.solvableItem?.text, ignoreCase = true)
    }


}
