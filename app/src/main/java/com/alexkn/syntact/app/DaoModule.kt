package com.alexkn.syntact.app

import com.alexkn.syntact.data.common.AppDatabase
import com.alexkn.syntact.data.dao.*
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
    fun provideTemplateDao(appDatabase: AppDatabase): TemplateDao = appDatabase.templateDao()

    @Singleton
    @Provides
    fun providePreferencesDao(appDatabase: AppDatabase): PreferencesDao = appDatabase.preferencesDao()

}