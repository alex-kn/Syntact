package com.alexkn.syntact.dataaccess.language;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.alexkn.syntact.dataaccess.common.AppDatabase;
import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.domain.repository.LanguagePairRepository;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class LanguagePairRepositoryImpl implements LanguagePairRepository {

    private LanguagePairDao languagePairDao;

    @Inject
    LanguagePairRepositoryImpl(Application application) {

        AppDatabase database = AppDatabase.getDatabase(application);
        languagePairDao = database.languagePairDao();
    }

    @Override
    public Long insert(LanguagePair languagePair) {

        return languagePairDao.insert(languagePair);
    }

    @Override
    public void delete(Long id) {

        languagePairDao.delete(id);
    }

    @Override
    public void update(LanguagePair languagePair) {

        languagePairDao.update(languagePair);
    }

    @Override
    public LanguagePair find(Long id) {

        return languagePairDao.find(id);
    }

    @Override
    public boolean languagePairExists(Locale left, Locale right) {

        return languagePairDao.languagePairExists(left, right);
    }

    @Override
    public LiveData<List<LanguagePair>> findAllLanguagePairs() {

        return languagePairDao.findAll();
    }

    @Override
    public void updateScore(Long id, int newScore) {

        AsyncTask.execute(() -> languagePairDao.updateScore(id, newScore));
    }

    @Override
    public void incrementScore(Long id, int by) {

        AsyncTask.execute(() -> languagePairDao.incrementScore(id, by));
    }

    @Override
    public LiveData<LanguagePair> findLanguagePair(Long id) {

        return languagePairDao.findLanguagePair(id);
    }
}
