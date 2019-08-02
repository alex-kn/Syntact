package com.alexkn.syntact.presentation.bucketdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alexkn.syntact.data.model.cto.SolvableTranslationCto
import com.alexkn.syntact.data.model.views.BucketDetail
import com.alexkn.syntact.domain.repository.BucketRepository
import com.alexkn.syntact.domain.repository.SolvableItemRepository
import javax.inject.Inject

class BucketDetailsViewModel @Inject constructor(
        private val solvableItemRepository: SolvableItemRepository,
        private val bucketRepository: BucketRepository
) : ViewModel() {

    lateinit var bucketDetail: LiveData<BucketDetail>

    lateinit var translations: LiveData<List<SolvableTranslationCto>>


    fun init(bucketId: Long) {
        bucketDetail = bucketRepository.getBucketDetail(bucketId)
        translations = solvableItemRepository.getSolvableTranslations(bucketId)
    }

    fun download() {
        bucketDetail.value?.let { solvableItemRepository.fetchSolvableItems(it.id) }
    }
}
