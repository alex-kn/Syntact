package dev.alexknittel.syntact.presentation.deckboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexknittel.syntact.core.model.DeckListItem
import dev.alexknittel.syntact.core.model.SolvableTranslationCto
import dev.alexknittel.syntact.core.repository.DeckRepository
import dev.alexknittel.syntact.core.repository.SolvableItemRepository
import dev.alexknittel.syntact.presentation.common.levenshteinDistanceTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject


class DeckBoardViewModel @Inject constructor(
        private val solvableItemRepository: SolvableItemRepository,
        private val deckRepository: DeckRepository
) : ViewModel() {

    var deck: MutableLiveData<DeckListItem> = MutableLiveData()
        private set

    var translation: MutableLiveData<SolvableTranslationCto?> = MutableLiveData()
        private set

    lateinit var items: List<SolvableTranslationCto>

    private val _maxScore: MutableLiveData<Int> = MutableLiveData(0)
    private val _currentScore: MutableLiveData<Int> = MutableLiveData(0)

    val maxScore: LiveData<Int>
        get() = _maxScore

    val currentScore: LiveData<Int>
        get() = _currentScore

    var done = false

    private var newCardsPerDay: Int = 1

    private var deckId: Long? = null

    val scoreRatio: Double
        get() = currentScore.value!!.toDouble() / maxScore.value!!


    fun init(deckId: Long) {
        this.deckId = deckId

        fetchNext()
    }

    fun fetchNext() {
        viewModelScope.launch(Dispatchers.Default) {
            newCardsPerDay = deckRepository.find(deckId!!).newItemsPerDay
            val d = deckRepository.find(deckId!!)
            val itemsSolvedToday = solvableItemRepository.findItemsSolvedOnDay(d.id!!, Instant.now()).size
            val itemsAttemptedToday = solvableItemRepository.findItemsAttemptedOnDay(d.id!!, Instant.now()).size
            val newItems = solvableItemRepository.findNewItemsForToday(d.id!!, d.newItemsPerDay).size
            val reviews = solvableItemRepository.findItemsDueForReview(d.id!!, Instant.now()).size
            deck.postValue(DeckListItem(d, itemsSolvedToday, itemsAttemptedToday, newItems, reviews))

            val nextTranslation = solvableItemRepository.findNextSolvableItem(deckId!!, Instant.now(), newCardsPerDay)
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

            if (!peek) {
                if (scoreRatio >= 0.9) {
                    viewModelScope.launch(Dispatchers.Default) { solvableItemRepository.markPhraseCorrect(translationCto, scoreRatio) }
                } else {
                    viewModelScope.launch(Dispatchers.Default) { solvableItemRepository.markPhraseIncorrect(translationCto, scoreRatio) }
                }
            }
            scoreRatio
        }
    }

    private fun getCurrentScore(translationCto: SolvableTranslationCto, solution: String): Int {
        val value = translationCto.solvableItem.text.length - translationCto.solvableItem.text.toLowerCase().levenshteinDistanceTo(solution.toLowerCase())
        return if (value >= 0) value else 0
    }


}
