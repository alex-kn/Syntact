package com.alexkn.syntact.dataaccess.bucket;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alexkn.syntact.domain.model.Bucket;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Dao
public interface BucketDao {

    @Insert
    Long insert(Bucket activeBucket);

    @Insert
    void insert(Collection<Bucket> activeBucketEntities);

    @Query("DELETE FROM Bucket where id = :id")
    void delete(Long id);

    @Update
    void update(Bucket bucket);

    @Query("SELECT * FROM Bucket where id = :id")
    Bucket find(Long id);

    @Query("SELECT * FROM Bucket")
    LiveData<List<Bucket>> findAll();

    @Query("SELECT * FROM Bucket WHERE id = :id LIMIT 1")
    LiveData<Bucket> findBucket(Long id);
}
