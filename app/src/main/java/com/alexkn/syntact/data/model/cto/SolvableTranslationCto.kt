package com.alexkn.syntact.data.model.cto

import androidx.room.Embedded
import com.alexkn.syntact.data.common.Identifiable
import com.alexkn.syntact.data.model.Clue
import com.alexkn.syntact.data.model.SolvableItem

data class SolvableTranslationCto(
        @Embedded var solvableItem: SolvableItem,
        @Embedded var clue: Clue?
) : Identifiable<Long> {
    override val id: Long
        get() = solvableItem.id
}
