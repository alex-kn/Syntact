package com.alexkn.syntact.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

import com.alexkn.syntact.data.dao.base.BaseDao
import com.alexkn.syntact.data.model.Bucket
import com.alexkn.syntact.data.model.views.BucketDetail

@Dao
interface BucketDao : BaseDao<Bucket> {

    @Query("DELETE FROM Bucket where id = :id")
    fun delete(id: Long)

    @Query("SELECT * FROM Bucket where id = :id")
    fun find(id: Long): Bucket

    @Query("SELECT * FROM Bucket")
    fun findAll(): LiveData<List<Bucket>>

    @Query("SELECT * FROM Bucket WHERE id = :id LIMIT 1")
    fun findBucket(id: Long): LiveData<Bucket>

    @Query("SELECT * FROM BucketDetail")
    fun findBucketDetails(): LiveData<List<BucketDetail>>

    @Query("SELECT * FROM BucketDetail WHERE id = :id LIMIT 1")
    fun findBucketDetail(id: Long) : LiveData<BucketDetail>
}
