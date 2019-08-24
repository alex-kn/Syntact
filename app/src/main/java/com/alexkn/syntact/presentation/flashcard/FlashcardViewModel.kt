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
import kotlinx.coroutines.Dispatchers
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

    var translation: MutableLiveData<SolvableTranslationCto?> = MutableLiveData()
        private set

    private var bucketId: Long? = null

    fun init(bucketId: Long) {

        this.bucketId = bucketId
        bucket = bucketRepository.getBucketDetail(bucketId)
        fetchNext()
    }

    fun fetchNext() {
        translation.value = null
        viewModelScope.launch(Dispatchers.Default) {
            val nextTranslation = solvableItemRepository.findNextSolvableTranslation(bucketId!!, Instant.now())
            translation.postValue(nextTranslation)
        }
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
