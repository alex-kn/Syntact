package dev.alexknittel.syntact.data.dao.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(t: T): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(t: Collection<T>)

    @Update
    suspend fun update(t: T)

    @Delete
    suspend fun delete(t: T)
}
