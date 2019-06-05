package com.alexkn.syntact.dataaccess.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.alexkn.syntact.dataaccess.dao.base.BaseDao;
import com.alexkn.syntact.domain.model.Bucket;

import java.util.List;

@Dao
public interface BucketDao extends BaseDao<Bucket> {

    @Query("DELETE FROM Bucket where id = :id")
    void delete(Long id);

    @Query("SELECT * FROM Bucket where id = :id")
    Bucket find(Long id);

    @Query("SELECT * FROM Bucket")
    LiveData<List<Bucket>> findAll();

    @Query("SELECT * FROM Bucket WHERE id = :id LIMIT 1")
    LiveData<Bucket> findBucket(Long id);
}
