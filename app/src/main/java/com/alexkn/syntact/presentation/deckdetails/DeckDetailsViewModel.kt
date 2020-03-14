package com.alexkn.syntact.presentation.deckdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexkn.syntact.core.repository.DeckRepository
import com.alexkn.syntact.core.repository.SolvableItemRepository
import com.alexkn.syntact.data.model.Deck
import com.alexkn.syntact.data.model.SolvableTranslationCto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeckDetailsViewModel @Inject constructor(
        private val solvableItemRepository: SolvableItemRepository,
        private val deckRepository: DeckRepository
) : ViewModel() {

    lateinit var deck: LiveData<Deck?>

    lateinit var translations: LiveData<List<SolvableTranslationCto>>

    fun init(bucketId: Long) {
        deck = deckRepository.findDeck(bucketId)
        translations = solvableItemRepository.getSolvableTranslations(bucketId)
    }

    fun save(name: String, maxCardsPerDay: String) {
        val deck = deck.value!!
        deck.name = name
        deck.newItemsPerDay = maxCardsPerDay.toIntOrNull() ?: 0
        viewModelScope.launch(Dispatchers.IO) { deckRepository.updateDeck(deck) }
    }

    fun deleteDeck() {
        GlobalScope.launch(Dispatchers.IO) { deckRepository.deleteDeck(deck.value!!.id!!) }
    }


}
