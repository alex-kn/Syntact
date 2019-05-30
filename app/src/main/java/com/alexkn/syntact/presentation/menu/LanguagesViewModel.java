package com.alexkn.syntact.presentation.menu;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.usecase.ManageBuckets;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;

import java.util.List;

import javax.inject.Inject;

public class LanguagesViewModel extends AndroidViewModel {


    @Inject
    ManageBuckets manageBuckets;
    private final LiveData<List<Bucket>> buckets;

    public LanguagesViewModel(@NonNull Application application) {

        super(application);

        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);

        buckets = manageBuckets.getBuckets();
    }

    public void deleteLanguage(Bucket bucket) {

        AsyncTask.execute(() -> manageBuckets.removeLanguage(bucket.getId()));
    }

    LiveData<List<Bucket>> getBuckets() {

        return buckets;
    }
}

