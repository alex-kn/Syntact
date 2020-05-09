package dev.alexknittel.syntact.data.config

import dagger.Module
import dagger.Provides
import dev.alexknittel.syntact.data.AppDatabase
import dev.alexknittel.syntact.data.dao.ClueDao
import dev.alexknittel.syntact.data.dao.DeckDao
import dev.alexknittel.syntact.data.dao.PreferencesDao
import dev.alexknittel.syntact.data.dao.SolvableItemDao
import javax.inject.Singleton

@Module
class AndroidTestDaoModule {

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