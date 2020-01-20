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

    var done = false

    private var bucketId: Long? = null

    fun init(bucketId: Long) {

        this.bucketId = bucketId
        deck = deckRepository.getBucketDetail(bucketId)
        fetchNext()
    }

    fun fetchNext() {
        viewModelScope.launch(Dispatchers.Default) {
            val nextTranslation = solvableItemRepository.findNextSolvableTranslation(bucketId!!, Instant.now())
            translation.postValue(nextTranslation)
        }
    }

    fun peekSolution(solution: String): Boolean {

        return if (translation.value?.solvableItem?.text.equals(solution, ignoreCase = true)) {
            viewModelScope.launch(Dispatchers.Default) { solvableItemRepository.solvePhrase(translation.value!!) }
            true
        } else false
    }

    fun checkSolution(solution: String): Boolean {
        return if (translation.value?.solvableItem?.text.equals(solution, ignoreCase = true)) {
            viewModelScope.launch(Dispatchers.Default) { solvableItemRepository.solvePhrase(translation.value!!) }
            true
        } else {
            viewModelScope.launch(Dispatchers.Default) { solvableItemRepository.phraseIncorrect(translation.value!!) }
            false
        }

    }

}
