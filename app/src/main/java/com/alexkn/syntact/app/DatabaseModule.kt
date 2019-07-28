package com.alexkn.syntact.app

import android.content.Context
import androidx.room.Room
import com.alexkn.syntact.data.common.AppDatabase
import com.alexkn.syntact.data.dao.BucketDao
import com.alexkn.syntact.domain.repository.BucketRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {

        return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database")
                .fallbackToDestructiveMigration().build()

    }


}