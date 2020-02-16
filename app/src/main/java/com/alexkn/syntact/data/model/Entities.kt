package com.alexkn.syntact.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.alexkn.syntact.data.common.Identifiable
import com.alexkn.syntact.data.common.TemplateType
import java.time.Instant
import java.util.*

@Entity
data class Deck(
        @PrimaryKey(autoGenerate = true) override var id: Long? = null,
        var name: String,
        var language: Locale,
        var createdAt: Instant = Instant.now(),
        var userLanguage: Locale,
        var itemCount: Int = 0
) : Identifiable<Long>

@Entity(foreignKeys = [ForeignKey(entity = Deck::class, parentColumns = ["id"], childColumns = ["deckId"], onDelete = ForeignKey.CASCADE)])
data class SolvableItem(
        @PrimaryKey(autoGenerate = true) override var id: Long? = null,
        @ColumnInfo(name = "solvableItemText") var text: String,
        var lastSolved: Instant? = null,
        var nextDueDate: Instant? = null,
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
data class Template(
        @PrimaryKey override val id: Long,
        var name: String,
        var templateType: TemplateType,
        var language: Locale,
        var phrasesUrl: String,
        var count: Int,
        var description: String
) : Identifiable<Long>

@Entity(foreignKeys = [ForeignKey(entity = Template::class, parentColumns = ["id"], childColumns = ["templateId"], onDelete = ForeignKey.CASCADE)])
data class Phrase(
        @PrimaryKey override var id: Long,
        var text: String,
        @ColumnInfo(index = true) var templateId: Long
) : Identifiable<Long>
