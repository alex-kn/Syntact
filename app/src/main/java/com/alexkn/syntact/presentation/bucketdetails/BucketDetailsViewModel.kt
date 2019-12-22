package com.alexkn.syntact.presentation.bucketdetails

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.alexkn.syntact.data.model.cto.SolvableTranslationCto
import com.alexkn.syntact.data.model.views.BucketDetail
import com.alexkn.syntact.core.repository.BucketRepository
import com.alexkn.syntact.core.repository.SolvableItemRepository
import com.alexkn.syntact.core.worker.FetchPhrasesWorker
import kotlinx.coroutines.launch
import javax.inject.Inject

class BucketDetailsViewModel @Inject constructor(
        private val solvableItemRepository: SolvableItemRepository,
        private val bucketRepository: BucketRepository,
        private val context: Context
) : ViewModel() {

    lateinit var bucketDetail: LiveData<BucketDetail>

    lateinit var translations: LiveData<List<SolvableTranslationCto>>

    fun init(bucketId: Long) {
        bucketDetail = bucketRepository.getBucketDetail(bucketId)
        translations = solvableItemRepository.getSolvableTranslations(bucketId)
    }

    fun disableItem(solvableTranslationCto: SolvableTranslationCto) = viewModelScope.launch {
        solvableItemRepository.disableSolvableItem(solvableTranslationCto)
    }

    fun download() {
        bucketDetail.value?.let { fetchSolvableItems(it.id) }
    }

    private fun fetchSolvableItems(bucketId: Long, count: Int? = null) {

        val data = Data.Builder().putLong("bucketId", bucketId)
        count?.let { data.putInt("count", count) }
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workRequest = OneTimeWorkRequest.Builder(FetchPhrasesWorker::class.java).setInputData(data.build()).setConstraints(constraints).build()
        WorkManager.getInstance(context).enqueueUniqueWork(FetchPhrasesWorker::class.java.name, ExistingWorkPolicy.KEEP, workRequest)
    }

}
