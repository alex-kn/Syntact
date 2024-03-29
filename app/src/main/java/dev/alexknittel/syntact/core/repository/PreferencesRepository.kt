package dev.alexknittel.syntact.core.repository

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import dev.alexknittel.syntact.core.model.Preferences
import dev.alexknittel.syntact.data.dao.PreferencesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepository @Inject constructor(
        private val preferencesDao: PreferencesDao
) {

    init {
        GlobalScope.launch(Dispatchers.IO) {
            var preferences = preferencesDao.findFirst()
            if (preferences == null) {
                preferences = Preferences(
                        language = Locale.getDefault(),
                        nightMode = AppCompatDelegate.MODE_NIGHT_NO
                )
                preferencesDao.insert(preferences)
            }
        }
    }


    fun findLive(): LiveData<Preferences?> = preferencesDao.findFirstLive()

    suspend fun find(): Preferences = preferencesDao.findFirst() ?: throw IllegalStateException("Preferences not found")

    suspend fun switchLanguage(locale: Locale) {
        val preferences = preferencesDao.findFirst()!!
        preferences.language = locale
        preferencesDao.update(preferences)
    }

    suspend fun save(preferences: Preferences) {
        preferencesDao.update(preferences)
    }

}