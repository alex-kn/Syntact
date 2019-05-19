package com.alexkn.syntact.dataaccess.phrase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.alexkn.syntact.dataaccess.common.AppDatabase;
import com.alexkn.syntact.domain.model.SolvableItem;
import com.alexkn.syntact.domain.repository.PhraseRepository;

import java.time.Instant;
import java.util.List;

import javax.inject.Inject;

public class PhraseRepositoryImpl implements PhraseRepository {

    private PhraseDao phraseDao;

    @Inject
    PhraseRepositoryImpl(Application application) {

        AppDatabase database = AppDatabase.getDatabase(application);
        phraseDao = database.phraseDao();
    }

    @Override
    public void updateAttempt(Long id, String attempt) {

        AsyncTask.execute(() -> phraseDao.updateAttempt(id, attempt));
    }

    @Override
    public void update(SolvableItem solvableItem) {

        AsyncTask.execute(() -> phraseDao.update(solvableItem));
    }

    @Override
    public LiveData<List<SolvableItem>> findPhrasesForBucketDueBefore(Long bucketId,
            Instant time) {

        return phraseDao.findPhrasesForBucketDueBefore(bucketId, time);
    }

    @Override
    public void insert(SolvableItem solvableItem) {

        AsyncTask.execute(() -> phraseDao.insert(solvableItem));
    }

    @Override
    public void insert(List<SolvableItem> solvableItems) {

        AsyncTask.execute(() -> phraseDao.insert(solvableItems));
    }
}
