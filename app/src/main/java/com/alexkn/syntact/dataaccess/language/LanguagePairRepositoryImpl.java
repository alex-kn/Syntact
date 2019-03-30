package com.alexkn.syntact.dataaccess.language;

import android.app.Application;
import android.os.AsyncTask;

import com.alexkn.syntact.dataaccess.common.AppDatabase;
import com.alexkn.syntact.dataaccess.util.Mapper;
import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.domain.repository.LanguagePairRepository;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

public class LanguagePairRepositoryImpl implements LanguagePairRepository {

    private LanguagePairDao languagePairDao;

    @Inject
    LanguagePairRepositoryImpl(Application application) {

        AppDatabase database = AppDatabase.getDatabase(application);
        languagePairDao = database.languagePairDao();
    }

    @Override
    public Long insert(LanguagePair languagePair) {

        return languagePairDao.insert(Mapper.toLanguagePairEntity(languagePair));
    }

    @Override
    public LiveData<List<LanguagePair>> findAllLanguagePairs() {

        return Transformations.map(languagePairDao.findAll(),
                input -> input.stream().map(Mapper::toLanguagePair).collect(Collectors.toList()));
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

        return Transformations.map(languagePairDao.find(id), Mapper::toLanguagePair);
    }
}
