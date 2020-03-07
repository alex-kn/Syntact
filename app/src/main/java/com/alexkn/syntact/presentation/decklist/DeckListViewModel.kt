package com.alexkn.syntact.presentation.decklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexkn.syntact.core.repository.DeckRepository
import com.alexkn.syntact.core.repository.PreferencesRepository
import com.alexkn.syntact.core.repository.SolvableItemRepository
import com.alexkn.syntact.data.model.DeckDetail
import com.alexkn.syntact.data.model.PlayerStats
import com.alexkn.syntact.data.model.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.*
import javax.inject.Inject

class DeckListViewModel @Inject
constructor(
        private val deckRepository: DeckRepository,
        private val solvableItemRepository: SolvableItemRepository,
        private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    val buckets: LiveData<List<DeckDetail>> = deckRepository.deckDetails
    val playerStats: LiveData<PlayerStats> = deckRepository.getPlayerStats()
    val preferences: LiveData<Preferences?> = preferencesRepository.findLive()

    private val maxNew = 20
    private val maxReviews = 500

    private val _newCards = MutableLiveData<Int>()
    val newCards: LiveData<Int>
        get() = _newCards

    private val _reviews = MutableLiveData<Int>()
    val reviews: LiveData<Int>
        get() = _reviews

    private val _total = MutableLiveData<Int>()
    val total: LiveData<Int>
        get() = _total

    fun init() {
        viewModelScope.launch(Dispatchers.IO) {

            var totalNewCards = 0
            var totalReviews = 0

            deckRepository.findAll().forEach {
                val itemsSolvedToday = solvableItemRepository.findItemsSolvedOnDay(it.id!!, Instant.now()).size
                val findNewItems = solvableItemRepository.findNewItems(it.id!!, maxNew)
                val newItems = findNewItems.size
                if (newItems - itemsSolvedToday >= 0) {
                    totalNewCards += newItems - itemsSolvedToday
                }
                totalReviews += solvableItemRepository.findItemsDueForReview(it.id!!, Instant.now()).size
            }
            _newCards.postValue(totalNewCards)
            _reviews.postValue(totalReviews)
            _total.postValue(totalNewCards + totalReviews)
        }
    }

    fun switchLanguage(language: Locale) = viewModelScope.launch {
        preferencesRepository.switchLanguage(language)
    }
}

