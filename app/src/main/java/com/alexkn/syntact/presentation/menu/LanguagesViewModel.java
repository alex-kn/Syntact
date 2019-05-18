package com.alexkn.syntact.presentation.menu;

import android.app.Application;
import android.os.AsyncTask;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.usecase.ManageBuckets;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LanguagesViewModel extends AndroidViewModel {

    private final LiveData<List<Bucket>> buckets;

    @Inject
    ManageBuckets manageBuckets;

    public LanguagesViewModel(@NonNull Application application) {

        super(application);

        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);

        AsyncTask.execute(() -> manageBuckets.addBucket(Locale.GERMAN, Locale.CHINESE));
        buckets = manageBuckets.getBuckets();
    }

    public void deleteLanguage(Bucket bucket) {

        AsyncTask.execute(() -> manageBuckets.removeLanguage(bucket.getId()));
    }

    LiveData<List<Bucket>> getBuckets() {

        return buckets;
    }
}

