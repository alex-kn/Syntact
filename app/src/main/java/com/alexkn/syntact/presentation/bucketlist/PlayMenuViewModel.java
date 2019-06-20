package com.alexkn.syntact.presentation.bucketlist;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alexkn.syntact.data.model.Bucket;
import com.alexkn.syntact.data.model.views.BucketDetail;
import com.alexkn.syntact.domain.repository.BucketRepository;

import java.util.List;

import javax.inject.Inject;

public class PlayMenuViewModel extends ViewModel {

    private BucketRepository bucketRepository;

    private LiveData<List<BucketDetail>> buckets;

    @Inject
    public PlayMenuViewModel(BucketRepository bucketRepository) {

        super();
        this.bucketRepository = bucketRepository;
        buckets = bucketRepository.getBucketDetails();
    }

    public void deleteLanguage(Bucket bucket) {

        AsyncTask.execute(() -> bucketRepository.removeLanguage(bucket));
    }

    LiveData<List<BucketDetail>> getBuckets() {

        return buckets;
    }
}

