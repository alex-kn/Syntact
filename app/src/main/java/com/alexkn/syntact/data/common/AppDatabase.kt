package com.alexkn.syntact.data.common

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.alexkn.syntact.data.dao.BucketDao
import com.alexkn.syntact.data.dao.ClueDao
import com.alexkn.syntact.data.dao.SolvableItemDao
import com.alexkn.syntact.data.model.Bucket
import com.alexkn.syntact.data.model.Clue
import com.alexkn.syntact.data.model.SolvableItem
import com.alexkn.syntact.data.model.views.BucketDetail
import com.alexkn.syntact.data.util.Converters

@Database(entities = [SolvableItem::class, Bucket::class, Clue::class], views = [BucketDetail::class], version = 34)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun solvableItemDao(): SolvableItemDao

    abstract fun bucketDao(): BucketDao

    abstract fun clueDao(): ClueDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database")
                        .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }

    }
}

