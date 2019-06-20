package com.alexkn.syntact.presentation.createbucket;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alexkn.syntact.domain.usecase.bucket.BucketRepository;
import com.alexkn.syntact.restservice.Template;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class CreateBucketViewModel extends ViewModel {

    private final List<Locale> availableBuckets;

    private final LiveData<List<Template>> availableTemplates;

    private BucketRepository bucketRepository;

    @Inject
    public CreateBucketViewModel(BucketRepository bucketRepository) {

        super();
        this.bucketRepository = bucketRepository;

        availableBuckets = bucketRepository.getAvailableLanguages();
        availableTemplates = bucketRepository.findAvailableTemplates();
    }

    void addBucket(Locale language, Template template, String words) {

        if (words.isEmpty()) {//TODO
            AsyncTask.execute(() -> bucketRepository
                    .addBucketWithExistingTemplate(language, template == null ? availableTemplates.getValue().get(0) : template));
        } else {
            List<String> phrases = Arrays.asList(StringUtils.split(words, " "));
            AsyncTask.execute(() -> bucketRepository.addBucketWithNewTemplate("AndroidTest", language, phrases));
        }
    }

    public List<Locale> getAvailableBuckets() {

        return availableBuckets;
    }

    public LiveData<List<Template>> getAvailableTemplates() {

        return availableTemplates;
    }
}
