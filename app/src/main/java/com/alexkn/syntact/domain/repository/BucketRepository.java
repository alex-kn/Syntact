package com.alexkn.syntact.domain.repository;

import androidx.lifecycle.LiveData;

import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.repository.base.BaseRepository;

import java.util.List;

public interface BucketRepository extends BaseRepository<Bucket> {

    Bucket find(Long id);

    LiveData<List<Bucket>> findAllBuckets();

    LiveData<Bucket> findBucket(Long id);
}
