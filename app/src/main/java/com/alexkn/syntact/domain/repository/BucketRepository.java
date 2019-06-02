package com.alexkn.syntact.domain.repository;

import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.model.views.BucketWithStats;

import java.util.List;
import java.util.Locale;

import androidx.lifecycle.LiveData;

public interface BucketRepository {

    Long insert(Bucket bucket);

    void delete(Long id);

    void update(Bucket bucket);

    Bucket find(Long id);

    LiveData<List<Bucket>> findAllBuckets();

    LiveData<Bucket> findBucket(Long id);

    BucketWithStats findBucketWithStats(Long id);
}
