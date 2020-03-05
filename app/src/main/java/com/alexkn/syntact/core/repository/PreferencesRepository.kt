package com.alexkn.syntact.core.repository

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import com.alexkn.syntact.data.dao.PreferencesDao
import com.alexkn.syntact.data.model.Preferences
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
        GlobalScope.launch {
            var preferences = preferencesDao.findFirst()
            if (preferences == null) {
                preferences = Preferences(
                        language = Locale.getDefault(),
                        nightMode = AppCompatDelegate.MODE_NIGHT_YES
                )
                preferencesDao.insert(preferences)
            }
        }
    }

    fun find(): LiveData<Preferences?> {
        return preferencesDao.findFirstLive()
    }

    suspend fun switchLanguage(locale: Locale) {

        val preferences = preferencesDao.findFirst()!!
        preferences.language = locale
        preferencesDao.update(preferences)
    }

}