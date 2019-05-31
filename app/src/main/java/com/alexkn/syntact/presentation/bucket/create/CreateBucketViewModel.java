package com.alexkn.syntact.presentation.bucket.create;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.usecase.bucket.CreateBucket;
import com.alexkn.syntact.domain.usecase.bucket.ManageBuckets;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class CreateBucketViewModel extends AndroidViewModel {

    private final List<Locale> availableBuckets;

    @Inject
    ManageBuckets manageBuckets;

    @Inject
    CreateBucket createBucket;

    public CreateBucketViewModel(@NonNull Application application) {

        super(application);

        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);

        availableBuckets = manageBuckets.getAvailableBuckets();
    }


    void addBucket(Locale language, int templateId) {

        AsyncTask.execute(() -> createBucket.addBucket(language,templateId ));
    }

    public List<Locale> getAvailableBuckets() {

        return availableBuckets;
    }
}
