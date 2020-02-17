package com.alexkn.syntact.presentation.decklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.alexkn.syntact.core.repository.DeckRepository
import com.alexkn.syntact.core.repository.SolvableItemRepository
import com.alexkn.syntact.data.model.DeckDetail
import com.alexkn.syntact.data.model.PlayerStats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant

import javax.inject.Inject

class DeckListViewModel @Inject
constructor(
        deckRepository: DeckRepository,
        solvableItemRepository: SolvableItemRepository
) : ViewModel() {

    val buckets: LiveData<List<DeckDetail>> = deckRepository.deckDetails

    val playerStats: LiveData<PlayerStats> = deckRepository.getPlayerStats()

    private val maxNew = 20

    private val _newCards = MutableLiveData<Int>()
    private val _reviews = MutableLiveData<Int>()

    val newCards: LiveData<Int>
        get() = _newCards

    val reviews: LiveData<Int>
        get() = _reviews


    init {
        viewModelScope.launch(Dispatchers.IO) {

            var totalNewCards = 0
            var totalReviews = 0

            deckRepository.findAll().forEach {
                val itemsSolvedToday = solvableItemRepository.findItemsSolvedOnDay(it.id!!, Instant.now()).size
                if (maxNew - itemsSolvedToday >= 0) {
                    totalNewCards += maxNew - itemsSolvedToday
                }
                totalReviews += solvableItemRepository.findItemsDueForReview(it.id!!, Instant.now()).size
            }

            _newCards.postValue(totalNewCards)
            _reviews.postValue(totalReviews)
        }
    }

}

