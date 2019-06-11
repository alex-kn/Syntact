package com.alexkn.syntact.presentation.play.menu;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.usecase.bucket.ManageBuckets;

import java.util.List;

import javax.inject.Inject;

public class PlayMenuViewModel extends ViewModel {

    ManageBuckets manageBuckets;

    private final LiveData<List<Bucket>> buckets;

    @Inject
    public PlayMenuViewModel( ManageBuckets manageBuckets) {

        super();
        this.manageBuckets = manageBuckets;
        buckets = manageBuckets.getBuckets();
    }

    public void deleteLanguage(Bucket bucket) {

        AsyncTask.execute(() -> manageBuckets.removeLanguage(bucket));
    }

    LiveData<List<Bucket>> getBuckets() {

        return buckets;
    }
}

