package com.alexkn.syntact.presentation.decklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.alexkn.syntact.core.repository.BucketRepository
import com.alexkn.syntact.data.model.BucketDetail
import com.alexkn.syntact.data.model.PlayerStats
import kotlinx.coroutines.launch

import javax.inject.Inject

class DeckListViewModel @Inject
constructor(bucketRepository: BucketRepository) : ViewModel() {

    val buckets: LiveData<List<BucketDetail>> = bucketRepository.bucketDetails

    val playerStats: LiveData<PlayerStats> = bucketRepository.getPlayerStats()

}

