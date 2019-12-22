package com.alexkn.syntact.presentation.bucketlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.alexkn.syntact.data.model.views.BucketDetail
import com.alexkn.syntact.data.model.views.PlayerStats
import com.alexkn.syntact.core.repository.BucketRepository
import kotlinx.coroutines.launch

import javax.inject.Inject

class PlayMenuViewModel @Inject
constructor(private val bucketRepository: BucketRepository) : ViewModel() {

    val buckets: LiveData<List<BucketDetail>> = bucketRepository.bucketDetails

    val playerStats: LiveData<PlayerStats> = bucketRepository.getPlayerStats()

    fun deleteBucket(id: Long) {
        viewModelScope.launch { bucketRepository.deleteBucket(id) }
    }
}

