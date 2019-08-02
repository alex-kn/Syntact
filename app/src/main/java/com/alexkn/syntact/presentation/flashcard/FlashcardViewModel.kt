package com.alexkn.syntact.presentation.flashcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexkn.syntact.data.model.Bucket
import com.alexkn.syntact.data.model.cto.SolvableTranslationCto
import com.alexkn.syntact.domain.repository.BucketRepository
import com.alexkn.syntact.domain.repository.SolvableItemRepository
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject


class FlashcardViewModel @Inject constructor(
        private val solvableItemRepository: SolvableItemRepository,
        private val bucketRepository: BucketRepository
) : ViewModel() {

    internal var bucket: LiveData<Bucket>? = null
        private set

    private var translations: LiveData<List<SolvableTranslationCto>>? = null

    var solvableTranslations = Array<MutableLiveData<SolvableTranslationCto?>>(2) { MutableLiveData() }

    private var bucketId: Long? = null

    fun init(bucketId: Long) {

        this.bucketId = bucketId
        bucket = bucketRepository.getBucket(bucketId)
        translations = solvableItemRepository.getDueSolvableTranslations(bucketId)
    }

    fun fetchNext(one: Boolean) {
        if (one) {
            viewModelScope.launch {
                val it = solvableItemRepository.getNextSolvableTranslations(bucketId, Instant.now(), 2)
                solvableTranslations[1].postValue(it.getOrNull(1))
                solvableTranslations[0].postValue(it.getOrNull(0))
            }
        } else {
            viewModelScope.launch {
                val it = solvableItemRepository.getNextSolvableTranslations(bucketId, Instant.now(), 2)
                solvableTranslations[0].postValue(it.getOrNull(1))
                solvableTranslations[1].postValue(it.getOrNull(0))

            }
        }
    }

    fun checkSolution(solution: String, one: Boolean): Boolean {
        if (one) {
            return if (solvableTranslations[0].value?.solvableItem?.text.equals(solution, ignoreCase = true)) {
                viewModelScope.launch {
                    solvableItemRepository.solvePhrase(solvableTranslations[0].value!!)
                }
                true

            } else {
                viewModelScope.launch {
                    solvableItemRepository.phraseIncorrect(solvableTranslations[0].value!!)
                }
                false
            }
        } else {
            return if (solvableTranslations[1].value?.solvableItem?.text.equals(solution, ignoreCase = true)) {
                viewModelScope.launch {
                    solvableItemRepository.solvePhrase(solvableTranslations[1].value!!)
                }
                true
            } else {
                viewModelScope.launch {
                    solvableItemRepository.phraseIncorrect(solvableTranslations[1].value!!)
                }

                false
            }
        }
    }
}
