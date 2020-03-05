package com.alexkn.syntact.presentation.deckdetails

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alexkn.syntact.core.repository.DeckRepository
import com.alexkn.syntact.core.repository.SolvableItemRepository
import com.alexkn.syntact.data.model.Deck
import com.alexkn.syntact.data.model.SolvableTranslationCto
import javax.inject.Inject

class DeckDetailsViewModel @Inject constructor(
        private val solvableItemRepository: SolvableItemRepository,
        private val deckRepository: DeckRepository,
        private val context: Context
) : ViewModel() {

    lateinit var deck: LiveData<Deck?>

    lateinit var translations: LiveData<List<SolvableTranslationCto>>

    fun init(bucketId: Long) {
        deck = deckRepository.findDeck(bucketId)
        translations = solvableItemRepository.getSolvableTranslations(bucketId)
    }


}
