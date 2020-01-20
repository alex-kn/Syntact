package com.alexkn.syntact.data.model

import androidx.room.DatabaseView
import com.alexkn.syntact.data.common.Identifiable
import java.time.Instant
import java.util.*

@DatabaseView("SELECT b.name, b.id, b.language, b.createdAt, b.itemCount, (SELECT count(*) FROM solvableitem s WHERE s.timesSolved > 0 AND s.deckId = b.id) as solvedCount, (SELECT count(*) FROM solvableitem s JOIN clue c ON s.id = c.clueSolvableItemId WHERE s.deckId = b.id) as onDeviceCount, (SELECT count(*) FROM solvableitem s WHERE (s.nextDueDate <= (SELECT strftime('%s', 'now') || substr(strftime('%f', 'now'), 4)) OR s.nextDueDate IS NULL) AND s.deckId = b.id) as dueCount FROM Deck b")
data class DeckDetail(
        override var id: Long,
        var name: String,
        var language: Locale,
        var createdAt: Instant,
        var itemCount: Int,
        var solvedCount: Int,
        var onDeviceCount: Int,
        var dueCount: Int
) : Identifiable<Long>

@DatabaseView("SELECT (SELECT sum(timesSolved) FROM solvableitem s) as solved, (SELECT count(*) FROM solvableitem s where s.lastSolved > strftime('%s','now','start of day')) as solvedToday")
data class PlayerStats(
        var solved: Int,
        var solvedToday: Int
)
