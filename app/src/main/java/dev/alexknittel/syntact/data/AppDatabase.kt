package dev.alexknittel.syntact.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.alexknittel.syntact.core.model.Clue
import dev.alexknittel.syntact.core.model.Deck
import dev.alexknittel.syntact.core.model.Preferences
import dev.alexknittel.syntact.core.model.SolvableItem
import dev.alexknittel.syntact.data.common.util.Converters
import dev.alexknittel.syntact.data.dao.ClueDao
import dev.alexknittel.syntact.data.dao.DeckDao
import dev.alexknittel.syntact.data.dao.PreferencesDao
import dev.alexknittel.syntact.data.dao.SolvableItemDao

@Database(
        entities = [SolvableItem::class, Deck::class, Clue::class, Preferences::class],
        version = 74)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun solvableItemDao(): SolvableItemDao

    abstract fun deckDao(): DeckDao

    abstract fun clueDao(): ClueDao

    abstract fun preferencesDao(): PreferencesDao

}

