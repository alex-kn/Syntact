package com.alexkn.syntact.data.model

import androidx.room.Embedded
import com.alexkn.syntact.data.common.Identifiable

data class SolvableTranslationCto(
        @Embedded var solvableItem: SolvableItem,
        @Embedded var clue: Clue
) : Identifiable<Long> {
    override val id: Long?
        get() = solvableItem.id
}
