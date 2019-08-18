package com.alexkn.syntact.presentation.flashcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexkn.syntact.data.model.Bucket
import com.alexkn.syntact.data.model.cto.SolvableTranslationCto
import com.alexkn.syntact.data.model.views.BucketDetail
import com.alexkn.syntact.domain.repository.BucketRepository
import com.alexkn.syntact.domain.repository.SolvableItemRepository
import com.alexkn.syntact.presentation.bucketdetails.BucketDetailsViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject


class FlashcardViewModel @Inject constructor(
        private val solvableItemRepository: SolvableItemRepository,
        private val bucketRepository: BucketRepository
) : ViewModel() {

    var bucket: LiveData<BucketDetail>? = null
        private set

//    private var solvableTranslations = Array<MutableLiveData<SolvableTranslationCto?>>(2) { MutableLiveData() }

    var translation: MutableLiveData<SolvableTranslationCto?> = MutableLiveData()
        private set


    private var bucketId: Long? = null

//    private var current = 1


//    val currentSolvableTranslation: MutableLiveData<SolvableTranslationCto?>
//        get() = solvableTranslations[current % 2]
//
//    val nextSolvableTranslation: MutableLiveData<SolvableTranslationCto?>
//        get() = solvableTranslations[(current + 1) % 2]

    fun init(bucketId: Long) {

        this.bucketId = bucketId
        bucket = bucketRepository.getBucketDetail(bucketId)
        fetchNext()
    }

    fun fetchNext() {

        translation.value = null
        viewModelScope.launch {
            val nextTranslation = solvableItemRepository.findNextSolvableTranslation(bucketId!!, Instant.now())
            translation.postValue(nextTranslation)
        }

//        current++
//        viewModelScope.launch {
//            val it = solvableItemRepository.getNextSolvableTranslations(bucketId, Instant.now(), 10)
//            currentSolvableTranslation.postValue(it.getOrNull(0))
//            nextSolvableTranslation.postValue(it.getOrNull(1))
//        }
    }

    fun checkSolution(solution: String): Boolean {
        return if (translation.value?.solvableItem?.text.equals(solution, ignoreCase = true)) {
            viewModelScope.launch {
                solvableItemRepository.solvePhrase(translation.value!!)
            }
            true
        } else {
            viewModelScope.launch {
                solvableItemRepository.phraseIncorrect(translation.value!!)
            }
            false
        }

    }
}
