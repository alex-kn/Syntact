package com.alexkn.syntact.presentation.bucket.create;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.usecase.bucket.CreateBucket;
import com.alexkn.syntact.domain.usecase.bucket.ManageBuckets;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;
import com.alexkn.syntact.restservice.Template;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class CreateBucketViewModel extends AndroidViewModel {

    private final List<Locale> availableBuckets;

    private final LiveData<List<Template>> availableTemplates;

    @Inject
    ManageBuckets manageBuckets;

    @Inject
    CreateBucket createBucket;

    public CreateBucketViewModel(@NonNull Application application) {

        super(application);

        DaggerViewComponent.builder().applicationComponent(((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);

        availableBuckets = manageBuckets.getAvailableLanguages();
        availableTemplates = createBucket.findAvailableTemplates();
    }

    void addBucket(Locale language, Template template) {

        if (template == null) {
            AsyncTask.execute(() -> createBucket.addBucket(language, availableTemplates.getValue().get(0)));
        }
    }

    public List<Locale> getAvailableBuckets() {

        return availableBuckets;
    }

    public LiveData<List<Template>> getAvailableTemplates() {

        return availableTemplates;
    }
}
