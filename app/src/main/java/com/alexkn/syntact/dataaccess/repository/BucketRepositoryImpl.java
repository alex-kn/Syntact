package com.alexkn.syntact.dataaccess.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alexkn.syntact.dataaccess.common.AppDatabase;
import com.alexkn.syntact.dataaccess.dao.BucketDao;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.model.views.BucketDetail;
import com.alexkn.syntact.domain.repository.BucketRepository;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class BucketRepositoryImpl implements BucketRepository {

    private BucketDao bucketDao;

    @Inject
    BucketRepositoryImpl(Application application) {

        AppDatabase database = AppDatabase.getDatabase(application);
        bucketDao = database.bucketDao();
    }

    @Override
    public Long insert(Bucket bucket) {

        return bucketDao.insert(bucket);
    }

    @Override
    public void insert(Collection<Bucket> t) {

        bucketDao.insert(t);
    }

    @Override
    public void update(Bucket bucket) {

        bucketDao.update(bucket);
    }

    @Override
    public void delete(Bucket bucket) {

        bucketDao.delete(bucket);
    }

    @Override
    public Bucket find(Long id) {

        return bucketDao.find(id);
    }

    @Override
    public LiveData<List<Bucket>> findAllBuckets() {

        return bucketDao.findAll();
    }

    @Override
    public LiveData<Bucket> findBucket(Long id) {

        return bucketDao.findBucket(id);
    }

    @Override
    public LiveData<List<BucketDetail>> findBucketDetails() {

        return bucketDao.findBucketDetails();
    }
}
