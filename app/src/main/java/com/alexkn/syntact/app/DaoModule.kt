package com.alexkn.syntact.app

import com.alexkn.syntact.data.common.AppDatabase
import com.alexkn.syntact.data.dao.BucketDao
import com.alexkn.syntact.data.dao.ClueDao
import com.alexkn.syntact.data.dao.ClueDao_Impl
import com.alexkn.syntact.data.dao.SolvableItemDao
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

}