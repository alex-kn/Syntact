package dev.alexknittel.syntact.data.config

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dev.alexknittel.syntact.data.AppDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {

        return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database").build()
    }
}