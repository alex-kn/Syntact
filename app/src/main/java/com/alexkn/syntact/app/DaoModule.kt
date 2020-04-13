package com.alexkn.syntact.app

import com.alexkn.syntact.data.common.AppDatabase
import com.alexkn.syntact.data.dao.ClueDao
import com.alexkn.syntact.data.dao.DeckDao
import com.alexkn.syntact.data.dao.PreferencesDao
import com.alexkn.syntact.data.dao.SolvableItemDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaoModule {

    @Singleton
    @Provides
    fun provideBucketDao(appDatabase: AppDatabase): DeckDao = appDatabase.deckDao()

    @Singleton
    @Provides
    fun provideClueDao(appDatabase: AppDatabase): ClueDao = appDatabase.clueDao()

    @Singleton
    @Provides
    fun provideSolvableItemDao(appDatabase: AppDatabase): SolvableItemDao = appDatabase.solvableItemDao()

    @Singleton
    @Provides
    fun providePreferencesDao(appDatabase: AppDatabase): PreferencesDao = appDatabase.preferencesDao()

}