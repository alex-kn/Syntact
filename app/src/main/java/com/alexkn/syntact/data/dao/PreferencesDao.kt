package com.alexkn.syntact.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.alexkn.syntact.core.model.Preferences
import com.alexkn.syntact.data.dao.base.BaseDao

@Dao
abstract class PreferencesDao : BaseDao<Preferences> {

    @Query("SELECT * FROM Preferences LIMIT 1")
    abstract suspend fun findFirst(): Preferences?

    @Query("SELECT * FROM Preferences LIMIT 1")
    abstract fun findFirstLive(): LiveData<Preferences?>

}