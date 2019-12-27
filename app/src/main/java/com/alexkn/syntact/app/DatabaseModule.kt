package com.alexkn.syntact.app

import android.content.Context
import androidx.room.Room
import com.alexkn.syntact.data.common.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {

//        return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database")
//                .fallbackToDestructiveMigration().build()

        return Room.inMemoryDatabaseBuilder(context.applicationContext, AppDatabase::class.java).build()


    }


}