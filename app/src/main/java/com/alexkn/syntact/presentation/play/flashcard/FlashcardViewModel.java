package com.alexkn.syntact.presentation.play.flashcard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.model.cto.SolvableTranslationCto;
import com.alexkn.syntact.domain.usecase.bucket.ManageBuckets;
import com.alexkn.syntact.domain.usecase.play.ManageSolvableItems;

import java.time.Instant;
import java.util.List;

import javax.inject.Inject;

public class FlashcardViewModel extends ViewModel {

    private final ManageSolvableItems manageSolvableItems;

    private final ManageBuckets manageBuckets;

    private final Instant startTime;

    private LiveData<Bucket> bucket;

    private LiveData<List<SolvableTranslationCto>> translations;

    private Long bucketId;

    @Inject
    public FlashcardViewModel(ManageSolvableItems manageSolvableItems, ManageBuckets manageBuckets) {

        super();
        this.manageSolvableItems = manageSolvableItems;
        this.manageBuckets = manageBuckets;
        this.startTime = Instant.now();
    }

    public void init(Long bucketId) {

        this.bucketId = bucketId;
        bucket = manageBuckets.getBucket(bucketId);
        translations = manageSolvableItems.getSolvableTranslations(bucketId);
    }

    LiveData<List<SolvableTranslationCto>> getTranslations() {

        return translations;
    }

    LiveData<Bucket> getBucket() {

        return bucket;
    }
}
