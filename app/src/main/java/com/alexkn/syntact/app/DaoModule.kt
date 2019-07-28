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
    fun provideBucketDao(appDatabase: AppDatabase): BucketDao {

        return appDatabase.bucketDao()
    }

    @Singleton
    @Provides
    fun provideClueDao(appDatabase: AppDatabase): ClueDao {

        return appDatabase.clueDao()
    }

    @Singleton
    @Provides
    fun provideSolvableItemDao(appDatabase: AppDatabase): SolvableItemDao {

        return appDatabase.solvableItemDao()
    }

    @Singleton
    @Provides
    fun providePlayerStatsDao(appDatabase: AppDatabase): PlayerStatsDao {

        return appDatabase.playerStatsDao()
    }

}