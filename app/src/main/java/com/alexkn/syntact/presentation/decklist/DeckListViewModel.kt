package com.alexkn.syntact.presentation.decklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexkn.syntact.core.repository.DeckRepository
import com.alexkn.syntact.core.repository.PreferencesRepository
import com.alexkn.syntact.core.repository.SolvableItemRepository
import com.alexkn.syntact.data.model.DeckListItem
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

    val preferences: LiveData<Preferences?> = preferencesRepository.findLive()

    private val maxNew = 20
    private val maxReviews = 500

    private val _decks = MutableLiveData<List<DeckListItem>>()
    val decks: LiveData<List<DeckListItem>>
        get() = _decks

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

            val deckListItems = deckRepository.findAll().map {
                val itemsSolvedToday = solvableItemRepository.findItemsSolvedOnDay(it.id!!, Instant.now()).size
                val newItems = solvableItemRepository.findNewItems(it.id!!, it.newItemsPerDay).size
                val reviews = solvableItemRepository.findItemsDueForReview(it.id!!, Instant.now()).size
                totalReviews += reviews
                totalNewCards += newItems
                DeckListItem(it, itemsSolvedToday, newItems, reviews)
            }
            _decks.postValue(deckListItems)
            _newCards.postValue(totalNewCards)
            _reviews.postValue(totalReviews)
            _total.postValue(totalNewCards + totalReviews)
        }
    }

    fun switchLanguage(language: Locale) = viewModelScope.launch {
        preferencesRepository.switchLanguage(language)
    }
}

