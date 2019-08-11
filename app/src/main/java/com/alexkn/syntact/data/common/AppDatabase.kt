package com.alexkn.syntact.data.common

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alexkn.syntact.data.dao.*
import com.alexkn.syntact.data.model.*

import com.alexkn.syntact.data.model.views.BucketDetail
import com.alexkn.syntact.data.model.views.PlayerStats
import com.alexkn.syntact.data.util.Converters

@Database(entities = [SolvableItem::class, Bucket::class, Clue::class, Template::class, Phrase::class], views = [BucketDetail::class, PlayerStats::class], version = 57)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun solvableItemDao(): SolvableItemDao

    abstract fun bucketDao(): BucketDao

    abstract fun clueDao(): ClueDao

    abstract fun playerStatsDao(): PlayerStatsDao

    abstract fun templateDao(): TemplateDao
}

