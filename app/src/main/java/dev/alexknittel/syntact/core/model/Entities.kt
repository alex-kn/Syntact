package dev.alexknittel.syntact.core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.*

@Entity
data class Deck(
        @PrimaryKey(autoGenerate = true) override var id: Long? = null,
        var name: String,
        var language: Locale,
        var createdAt: Instant = Instant.now(),
        var userLanguage: Locale,
        var itemCount: Int = 0,
        var newItemsPerDay: Int
) : Identifiable<Long>

@Entity(foreignKeys = [ForeignKey(entity = Deck::class, parentColumns = ["id"], childColumns = ["deckId"], onDelete = ForeignKey.CASCADE)])
data class SolvableItem(
        @PrimaryKey(autoGenerate = true) override var id: Long? = null,
        @ColumnInfo(name = "solvableItemText") var text: String,
        var lastSolved: Instant? = null,
        var lastFailed: Instant? = null,
        var lastAttempt: Instant? = null,
        var nextDueDate: Instant? = null,
        var firstSeen: Instant? = null,
        var easiness: Float = 2.5f,
        var consecutiveCorrectAnswers: Int = 0,
        var timesSolved: Int = 0,
        @ColumnInfo(index = true) var deckId: Long? = null
) : Identifiable<Long>

@Entity(foreignKeys = [ForeignKey(entity = SolvableItem::class, parentColumns = ["id"], childColumns = ["clueSolvableItemId"], onDelete = ForeignKey.CASCADE)])
data class Clue(
        @ColumnInfo(name = "clueId") @PrimaryKey(autoGenerate = true) var id: Long? = null,
        @ColumnInfo(name = "clueText") var text: String,
        @ColumnInfo(name = "clueSolvableItemId", index = true) var solvableItemId: Long? = null
)

@Entity
data class Preferences(
        @PrimaryKey override var id: Long = 1L,
        var language: Locale,
        var nightMode: Int
) : Identifiable<Long>
