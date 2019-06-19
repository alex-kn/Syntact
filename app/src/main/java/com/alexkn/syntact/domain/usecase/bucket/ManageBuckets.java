package com.alexkn.syntact.domain.usecase.bucket;

import androidx.lifecycle.LiveData;

import com.alexkn.syntact.app.Property;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.model.views.BucketDetail;
import com.alexkn.syntact.domain.repository.BucketRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ManageBuckets {

    BucketRepository bucketRepository;

    Property property;

    @Inject
    ManageBuckets(BucketRepository bucketRepository, Property property) {

        this.bucketRepository = bucketRepository;
        this.property = property;
    }

    public List<Locale> getAvailableLanguages() {

        String[] languages = property.get("available-languages").split(",");
        return Arrays.stream(languages).map(Locale::new).collect(Collectors.toList());
    }

    public void removeLanguage(Bucket bucket) {

        bucketRepository.delete(bucket);
    }

    public LiveData<Bucket> getBucket(Long id) {

        return bucketRepository.findBucket(id);
    }

    public LiveData<List<BucketDetail>> getBucketDetails() {

        return bucketRepository.findBucketDetails();
    }
}
