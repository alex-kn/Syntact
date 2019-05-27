package com.alexkn.syntact.dataaccess.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.alexkn.syntact.dataaccess.common.AppDatabase;
import com.alexkn.syntact.dataaccess.dao.PhraseDao;
import com.alexkn.syntact.domain.model.SolvableTranslation;
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
    public void update(SolvableTranslation solvableTranslation) {

        AsyncTask.execute(() -> phraseDao.update(solvableTranslation));
    }

    @Override
    public LiveData<List<SolvableTranslation>> findPhrasesForBucketDueBefore(Long bucketId,
            Instant time) {

        return phraseDao.findPhrasesForBucketDueBefore(bucketId, time);
    }

    @Override
    public void insert(SolvableTranslation solvableTranslation) {

        AsyncTask.execute(() -> phraseDao.insert(solvableTranslation));
    }

    @Override
    public void insert(List<SolvableTranslation> solvableTranslations) {

        AsyncTask.execute(() -> phraseDao.insert(solvableTranslations));
    }
}
