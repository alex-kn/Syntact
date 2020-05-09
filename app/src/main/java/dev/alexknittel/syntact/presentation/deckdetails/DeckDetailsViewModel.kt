package dev.alexknittel.syntact.presentation.deckdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexknittel.syntact.core.model.Deck
import dev.alexknittel.syntact.core.model.SolvableTranslationCto
import dev.alexknittel.syntact.core.repository.DeckRepository
import dev.alexknittel.syntact.core.repository.SolvableItemRepository
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

    fun save(name: String, maxCardsPerDay: String) = viewModelScope.launch(Dispatchers.IO) {
        val deck = deck.value!!
        deck.name = name
        deck.newItemsPerDay = maxCardsPerDay.toIntOrNull() ?: 0
        deckRepository.updateDeck(deck)
    }

    fun deleteDeck() = GlobalScope.launch(Dispatchers.IO) {
        deckRepository.deleteDeck(deck.value!!.id!!)
    }

    fun deleteCard(solvableTranslationCto: SolvableTranslationCto) = viewModelScope.launch(Dispatchers.IO) {
        solvableItemRepository.deleteSolvableTranslationCto(solvableTranslationCto)
    }
}
