package com.alexkn.syntact.domain.usecase;

import androidx.lifecycle.LiveData;

import com.alexkn.syntact.app.Property;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.repository.BucketRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ManageBuckets {

    @Inject
    BucketRepository bucketRepository;

    @Inject
    ManageLetters manageLetters;

    @Inject
    ManagePhrases managePhrases;

    @Inject
    Property property;

    @Inject
    ManageBuckets() {

    }

    public void addBucket(Locale languageLeft, Locale languageRight) {

        if (bucketRepository.bucketExists(languageLeft, languageRight)) {
            return;
        }

        Bucket activeBucket = new Bucket();
        activeBucket.setLanguageLeft(languageLeft);
        activeBucket.setLanguageRight(languageRight);
        activeBucket.setStreak(0);
        activeBucket.setDailyAverage(0);
        Long insertedLanguageId = bucketRepository.insert(activeBucket);
        manageLetters.initializeLetters(insertedLanguageId);
        managePhrases.initializePhrases(insertedLanguageId, languageLeft, languageRight);
    }

    public List<Locale> getAvailableBuckets() {

        String[] languages = property.get("available-languages").split(",");
        return Arrays.stream(languages).map(Locale::new)
                .collect(Collectors.toList());
    }

    public void removeLanguage(Long id) {

        bucketRepository.delete(id);
    }

    public LiveData<Bucket> getBucket(Long id) {

        return bucketRepository.findBucket(id);
    }

    public LiveData<List<Bucket>> getBuckets() {

        return bucketRepository.findAllBuckets();
    }
}
