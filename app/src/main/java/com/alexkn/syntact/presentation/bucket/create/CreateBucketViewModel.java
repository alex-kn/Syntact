package com.alexkn.syntact.presentation.bucket.create;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alexkn.syntact.domain.usecase.bucket.CreateBucket;
import com.alexkn.syntact.domain.usecase.bucket.ManageBuckets;
import com.alexkn.syntact.restservice.Template;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class CreateBucketViewModel extends ViewModel {

    private final List<Locale> availableBuckets;

    private final LiveData<List<Template>> availableTemplates;

    ManageBuckets manageBuckets;

    CreateBucket createBucket;

    @Inject
    public CreateBucketViewModel(ManageBuckets manageBuckets, CreateBucket createBucket) {

        super();
        this.manageBuckets = manageBuckets;
        this.createBucket = createBucket;

        availableBuckets = manageBuckets.getAvailableLanguages();
        availableTemplates = createBucket.findAvailableTemplates();
    }

    void addBucket(Locale language, Template template, String words) {

        if (words.isEmpty()) {//TODO
            AsyncTask.execute(
                    () -> createBucket.addBucketWithExistingTemplate(language, template == null ? availableTemplates.getValue().get(0) : template));
        } else {
            List<String> phrases = Arrays.asList(StringUtils.split(words, " "));
            AsyncTask.execute(() -> createBucket.addBucketWithNewTemplate("AndroidTest", language, phrases));
        }
    }

    public List<Locale> getAvailableBuckets() {

        return availableBuckets;
    }

    public LiveData<List<Template>> getAvailableTemplates() {

        return availableTemplates;
    }
}
