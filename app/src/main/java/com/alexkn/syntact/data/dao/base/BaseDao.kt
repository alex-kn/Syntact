package com.alexkn.syntact.data.dao.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(t: T): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(t: Collection<T>)

    @Update
    fun update(t: T)

    @Delete
    fun delete(t: T)
}
