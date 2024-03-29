package dev.alexknittel.syntact.presentation.decklist

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexknittel.syntact.core.model.DeckListItem
import dev.alexknittel.syntact.core.model.Preferences
import dev.alexknittel.syntact.core.repository.ConfigRepository
import dev.alexknittel.syntact.core.repository.DeckRepository
import dev.alexknittel.syntact.core.repository.PreferencesRepository
import dev.alexknittel.syntact.core.repository.SolvableItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.*
import javax.inject.Inject

class DeckListViewModel @Inject
constructor(
        private val deckRepository: DeckRepository,
        private val solvableItemRepository: SolvableItemRepository,
        private val preferencesRepository: PreferencesRepository,
        configRepository: ConfigRepository
) : ViewModel() {

    val preferences: LiveData<Preferences?> = preferencesRepository.findLive()

    val languageChoices = configRepository.availableLanguages

    var nearestDueDate: Instant? = null

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

    fun refreshDeckList(userLang: Locale = preferences.value!!.language) = viewModelScope.launch(Dispatchers.Default) {

        nearestDueDate = solvableItemRepository.findNearestDueDate(userLang)

        var totalNewCards = 0
        var totalReviews = 0

        val deckListItems = deckRepository.find(userLang).map {
            val itemsSolvedToday = solvableItemRepository.findItemsSolvedOnDay(it.id!!, Instant.now()).size
            val itemsAttemptedToday = solvableItemRepository.findItemsAttemptedOnDay(it.id!!, Instant.now()).size
            val newItems = solvableItemRepository.findNewItemsForToday(it.id!!, it.newItemsPerDay).size
            val reviews = solvableItemRepository.findItemsDueForReview(it.id!!, Instant.now()).size
            totalReviews += reviews
            totalNewCards += newItems
            DeckListItem(it, itemsSolvedToday, itemsAttemptedToday, newItems, reviews)
        }
        _decks.postValue(deckListItems)
        _newCards.postValue(totalNewCards)
        _reviews.postValue(totalReviews)
        _total.postValue(totalNewCards + totalReviews)

    }

    fun switchNightMode() {
        val nightModeChoices = arrayOf(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM, AppCompatDelegate.MODE_NIGHT_YES, AppCompatDelegate.MODE_NIGHT_NO)
        val prefs = preferences.value!!
        prefs.nightMode = nightModeChoices[(nightModeChoices.indexOf(prefs.nightMode) + 1) % nightModeChoices.size]
        viewModelScope.launch(Dispatchers.IO) { preferencesRepository.save(prefs) }
    }

    fun switchLanguage(locale: Locale) = viewModelScope.launch {
        preferencesRepository.switchLanguage(locale)
    }
}

