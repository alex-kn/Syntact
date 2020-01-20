package com.alexkn.syntact.data.common

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alexkn.syntact.data.dao.*
import com.alexkn.syntact.data.model.*
import com.alexkn.syntact.data.util.Converters

@Database(entities = [SolvableItem::class, Deck::class, Clue::class, Template::class, Phrase::class], views = [DeckDetail::class, PlayerStats::class], version = 70)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun solvableItemDao(): SolvableItemDao

    abstract fun deckDao(): DeckDao

    abstract fun clueDao(): ClueDao

    abstract fun playerStatsDao(): PlayerStatsDao

    abstract fun templateDao(): TemplateDao
}

