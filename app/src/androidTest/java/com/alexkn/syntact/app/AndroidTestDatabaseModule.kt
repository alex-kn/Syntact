package com.alexkn.syntact.app

import android.content.Context
import androidx.room.Room
import com.alexkn.syntact.data.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AndroidTestDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {

        return Room.inMemoryDatabaseBuilder(context.applicationContext, AppDatabase::class.java).build()
    }
}