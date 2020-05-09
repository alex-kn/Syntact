package dev.alexknittel.syntact.data.config

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import dagger.Module
import dagger.Provides
import dev.alexknittel.syntact.core.model.Preferences
import dev.alexknittel.syntact.data.AppDatabase
import dev.alexknittel.syntact.data.dao.ClueDao
import dev.alexknittel.syntact.data.dao.DeckDao
import dev.alexknittel.syntact.data.dao.PreferencesDao
import dev.alexknittel.syntact.data.dao.SolvableItemDao
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import java.util.*
import javax.inject.Singleton

@Module
class TestDaoModule {

    @Singleton
    @Provides
    fun provideBucketDao(appDatabase: AppDatabase): DeckDao = mockk()

    @Singleton
    @Provides
    fun provideClueDao(appDatabase: AppDatabase): ClueDao = mockk()

    @Singleton
    @Provides
    fun provideSolvableItemDao(appDatabase: AppDatabase): SolvableItemDao = mockk()

    @Singleton
    @Provides
    fun providePreferencesDao(appDatabase: AppDatabase): PreferencesDao {

        val preferencesDao = mockk<PreferencesDao>()
        val defaultPreferences = Preferences(language = Locale.getDefault(), nightMode = AppCompatDelegate.MODE_NIGHT_NO)
        coEvery { preferencesDao.findFirst() }.returns(defaultPreferences)
        every { preferencesDao.findFirstLive() }.returns(MutableLiveData(defaultPreferences))
        return preferencesDao
    }
}