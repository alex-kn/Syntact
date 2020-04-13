package com.alexkn.syntact.core.model

import androidx.room.Embedded
import com.alexkn.syntact.data.common.Identifiable

data class SolvableTranslationCto(
        @Embedded var solvableItem: SolvableItem,
        @Embedded var clue: Clue
) : Identifiable<Long> {
    override val id: Long?
        get() = solvableItem.id
}

data class DeckListItem(
        var deck: Deck,
        var solvedToday: Int,
        var newItemsToday: Int,
        var reviewsToday: Int
) : Identifiable<Long> {
    override val id: Long?
        get() = deck.id
}