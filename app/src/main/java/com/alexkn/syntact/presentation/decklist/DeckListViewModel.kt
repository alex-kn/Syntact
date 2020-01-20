package com.alexkn.syntact.presentation.decklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import com.alexkn.syntact.core.repository.DeckRepository
import com.alexkn.syntact.data.model.DeckDetail
import com.alexkn.syntact.data.model.PlayerStats

import javax.inject.Inject

class DeckListViewModel @Inject
constructor(deckRepository: DeckRepository) : ViewModel() {

    val buckets: LiveData<List<DeckDetail>> = deckRepository.deckDetails

    val playerStats: LiveData<PlayerStats> = deckRepository.getPlayerStats()

}

