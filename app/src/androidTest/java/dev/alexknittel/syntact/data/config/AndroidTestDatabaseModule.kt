package dev.alexknittel.syntact.data.config

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dev.alexknittel.syntact.data.AppDatabase
import javax.inject.Singleton


@Module
class AndroidTestDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {

        return Room.inMemoryDatabaseBuilder(context.applicationContext, AppDatabase::class.java).build()
    }
}