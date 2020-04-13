package com.alexkn.syntact.data.common

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alexkn.syntact.core.model.Clue
import com.alexkn.syntact.core.model.Deck
import com.alexkn.syntact.core.model.Preferences
import com.alexkn.syntact.core.model.SolvableItem
import com.alexkn.syntact.data.common.util.Converters
import com.alexkn.syntact.data.dao.ClueDao
import com.alexkn.syntact.data.dao.DeckDao
import com.alexkn.syntact.data.dao.PreferencesDao
import com.alexkn.syntact.data.dao.SolvableItemDao

@Database(
        entities = [SolvableItem::class, Deck::class, Clue::class, Preferences::class],
        version = 73)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun solvableItemDao(): SolvableItemDao

    abstract fun deckDao(): DeckDao

    abstract fun clueDao(): ClueDao

    abstract fun preferencesDao(): PreferencesDao

}

