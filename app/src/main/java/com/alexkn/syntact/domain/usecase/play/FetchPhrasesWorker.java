package com.alexkn.syntact.domain.usecase.play;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.app.Property;
import com.alexkn.syntact.domain.model.cto.SolvableTranslationCto;
import com.alexkn.syntact.domain.repository.BucketRepository;
import com.alexkn.syntact.domain.repository.ClueRepository;
import com.alexkn.syntact.domain.repository.SolvableItemRepository;
import com.alexkn.syntact.restservice.SolvableItemRemoteRepository;
import com.alexkn.syntact.restservice.SyntactService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.inject.Inject;

public class FetchPhrasesWorker extends Worker {

    private static final String TAG = FetchPhrasesWorker.class.getSimpleName();

    @Inject
    BucketRepository bucketRepository;

    @Inject
    SolvableItemRepository solvableItemRepository;

    @Inject
    SolvableItemRemoteRepository solvableItemRemoteRepository;

    @Inject
    ClueRepository clueRepository;

    @Inject
    SyntactService syntactService;

    @Inject
    Property property;

    public FetchPhrasesWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {

        super(context, workerParams);
        ((ApplicationComponentProvider) context).getApplicationComponent().inject(this);
    }

    @NonNull
    @Override
    public Result doWork() {

        //TODO do only if items on phone <= itemcount in bucket
        //TODO regularly update bucket phrase count
        Data inputData = getInputData();
        long bucketId = inputData.getLong("bucketId", -1L);
        long timestamp = inputData.getLong("timestamp", Instant.now().toEpochMilli());
        Instant now = Instant.ofEpochMilli(timestamp).truncatedTo(ChronoUnit.MINUTES);
        if (bucketId == -1L) {
            return Result.failure();
        }

        List<SolvableTranslationCto> solvableTranslationCtos = solvableItemRemoteRepository.fetchNewTranslations(bucketId, now, 10);
        solvableTranslationCtos.forEach(cto -> {
            solvableItemRepository.insert(cto.getSolvableItem());
            clueRepository.insert(cto.getClue());
        });

        return Result.success();
    }
}
