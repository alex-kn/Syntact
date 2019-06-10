package com.alexkn.syntact.presentation.bucket.list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.model.views.BucketDetail;
import com.alexkn.syntact.domain.usecase.bucket.ManageBuckets;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;

import java.util.List;

import javax.inject.Inject;

public class ListBucketsViewModel extends AndroidViewModel {

    private LiveData<List<BucketDetail>> buckets;

    @Inject
    ManageBuckets manageBuckets;

    public ListBucketsViewModel(@NonNull Application application) {

        super(application);

        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);

        buckets = manageBuckets.getBucketDetails();
    }

    public LiveData<List<BucketDetail>> getBuckets() {

        return buckets;
    }
}
