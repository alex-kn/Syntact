package dev.alexknittel.syntact.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import dev.alexknittel.syntact.core.model.Preferences
import dev.alexknittel.syntact.data.dao.base.BaseDao

@Dao
abstract class PreferencesDao : BaseDao<Preferences> {

    @Query("SELECT * FROM Preferences LIMIT 1")
    abstract suspend fun findFirst(): Preferences?

    @Query("SELECT * FROM Preferences LIMIT 1")
    abstract fun findFirstLive(): LiveData<Preferences?>

}