package com.alexkn.syntact.presentation.bucket.list;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.usecase.bucket.ManageBuckets;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;

import java.util.List;

import javax.inject.Inject;

public class ListBucketsViewModel extends AndroidViewModel {

    private LiveData<List<Bucket>> buckets;

    @Inject
    ManageBuckets manageBuckets;

    public ListBucketsViewModel(@NonNull Application application) {

        super(application);

        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);

        buckets = manageBuckets.getBuckets();
    }

    public LiveData<List<Bucket>> getBuckets() {

        return buckets;
    }
}
