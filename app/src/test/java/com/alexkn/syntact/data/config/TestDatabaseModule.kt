package com.alexkn.syntact.data.config

import android.content.Context
import androidx.room.Room
import com.alexkn.syntact.data.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class TestDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {

        return Room.inMemoryDatabaseBuilder(context.applicationContext, AppDatabase::class.java).build()
    }
}