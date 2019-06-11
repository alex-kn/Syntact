package com.alexkn.syntact.presentation.bucket.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alexkn.syntact.domain.model.views.BucketDetail;
import com.alexkn.syntact.domain.usecase.bucket.ManageBuckets;

import java.util.List;

import javax.inject.Inject;

public class ListBucketsViewModel extends ViewModel {

    private LiveData<List<BucketDetail>> buckets;

    ManageBuckets manageBuckets;

    @Inject
    public ListBucketsViewModel(ManageBuckets manageBuckets) {

        super();
        this.manageBuckets = manageBuckets;

        buckets = manageBuckets.getBucketDetails();
    }

    public LiveData<List<BucketDetail>> getBuckets() {

        return buckets;
    }
}
