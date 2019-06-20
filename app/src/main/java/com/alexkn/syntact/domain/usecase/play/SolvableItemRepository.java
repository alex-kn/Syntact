package com.alexkn.syntact.domain.usecase.play;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.alexkn.syntact.dataaccess.common.AppDatabase;
import com.alexkn.syntact.dataaccess.dao.BucketDao;
import com.alexkn.syntact.dataaccess.dao.SolvableItemDao;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.model.SolvableItem;
import com.alexkn.syntact.domain.model.cto.SolvableTranslationCto;
import com.alexkn.syntact.restservice.SolvableItemRemoteRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Maybe;

@Singleton
public class SolvableItemRepository {

    private static final String TAG = SolvableItemRepository.class.getSimpleName();

    private SolvableItemRemoteRepository solvableItemRemoteRepository;

    private SolvableItemDao solvableItemDao;

    private BucketDao bucketDao;

    @Inject
    SolvableItemRepository(SolvableItemRemoteRepository solvableItemRemoteRepository, Context context) {

        this.solvableItemRemoteRepository = solvableItemRemoteRepository;
        solvableItemDao = AppDatabase.getDatabase(context).solvableItemDao();
        bucketDao = AppDatabase.getDatabase(context).bucketDao();
    }

    public LiveData<List<SolvableTranslationCto>> getSolvableTranslations(Long bucketId) {

        return solvableItemDao.getSolvableTranslationsDueBefore(bucketId, Instant.now());
    }

    public Maybe<SolvableTranslationCto[]> getNextSolvableTranslations(Long bucketId, Instant time, int count) {

        Maybe<SolvableTranslationCto[]> translations = solvableItemDao.getNextTranslationDueBefore(bucketId, time, count);

        return translations.flatMap(items -> {
            if (items.length == count) {
                Log.i(TAG, "All translations present");
                return Maybe.just(items);
            } else {
                Log.i(TAG, "Translations missing, fetching remote");
                Bucket bucket = bucketDao.find(bucketId);
                long maxId = solvableItemDao.getMaxId(bucketId);
                List<SolvableTranslationCto> solvableTranslationCtos = solvableItemRemoteRepository.fetchNewTranslations(bucket, time, maxId, count);
                //TODO Service to return Maybe
                return Maybe.just(solvableTranslationCtos.toArray(new SolvableTranslationCto[0]));
            }
        });
    }

    public void fetchSolvableItems(long bucketId, Instant startTime) {

        Data data = new Data.Builder().putLong("bucketId", bucketId).putLong("timestamp", startTime.toEpochMilli()).build();

        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(FetchPhrasesWorker.class).setInputData(data).build();
        WorkManager.getInstance().enqueueUniqueWork(FetchPhrasesWorker.class.getName(), ExistingWorkPolicy.KEEP, workRequest);
    }

    public void solvePhrase(SolvableTranslationCto solvableTranslation) {

        SolvableItem solvableItem = solvableTranslation.getSolvableItem();
        Log.i(TAG, "Solving Phrase " + solvableItem.getText());

        int performanceRating = 3;
        float easiness = solvableItem.getEasiness();
        easiness += -0.80 + 0.28 * performanceRating + 0.02 * performanceRating * performanceRating;

        int consecutiveCorrectAnswers = solvableItem.getConsecutiveCorrectAnswers() + 1;

        double daysToAdd = 6 * Math.pow(easiness, consecutiveCorrectAnswers - 1d);
        Instant nextDueDate = Instant.now().plus(Math.round(daysToAdd), ChronoUnit.DAYS);

        solvableItem.setEasiness(easiness);
        solvableItem.setConsecutiveCorrectAnswers(consecutiveCorrectAnswers);
        solvableItem.setNextDueDate(nextDueDate);
        solvableItem.setLastSolved(Instant.now());
        solvableItem.setTimesSolved(solvableItem.getTimesSolved() + 1);

        solvableItemDao.update(solvableItem);
    }
}
