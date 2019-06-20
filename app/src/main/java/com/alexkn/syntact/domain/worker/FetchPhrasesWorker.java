package com.alexkn.syntact.domain.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.app.Property;
import com.alexkn.syntact.data.common.AppDatabase;
import com.alexkn.syntact.data.dao.ClueDao;
import com.alexkn.syntact.data.dao.SolvableItemDao;
import com.alexkn.syntact.restservice.SolvableItemService;
import com.alexkn.syntact.restservice.SyntactService;

import javax.inject.Inject;

public class FetchPhrasesWorker extends Worker {

    private static final String TAG = FetchPhrasesWorker.class.getSimpleName();

    private final SolvableItemDao solvableItemDao;

    private final ClueDao clueDao;

    @Inject
    SolvableItemService solvableItemService;

    @Inject
    SyntactService syntactService;

    @Inject
    Property property;

    public FetchPhrasesWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {

        super(context, workerParams);
        ((ApplicationComponentProvider) context).getApplicationComponent().inject(this);

        AppDatabase database = AppDatabase.getDatabase(context);
        solvableItemDao = database.solvableItemDao();
        clueDao = database.clueDao();
    }

    @NonNull
    @Override
    public Result doWork() {

        //TODO do only if items on phone <= itemcount in bucket
        //TODO regularly update bucket phrase count
//        Data inputData = getInputData();
//        long bucketId = inputData.getLong("bucketId", -1L);
//        long timestamp = inputData.getLong("timestamp", Instant.now().toEpochMilli());
//        Instant now = Instant.ofEpochMilli(timestamp).truncatedTo(ChronoUnit.MINUTES);
//        if (bucketId == -1L) {
//            return Result.failure();
//        }
//
//        List<SolvableTranslationCto> solvableTranslationCtos = solvableItemService.fetchNewTranslations(bucketId, now, 10);
//        solvableTranslationCtos.forEach(cto -> {
//            solvableItemDao.insert(cto.getSolvableItem());
//            clueDao.insert(cto.getClue());
//        });

        return Result.success();
    }
}
