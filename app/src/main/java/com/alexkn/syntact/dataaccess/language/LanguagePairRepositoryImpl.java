package com.alexkn.syntact.dataaccess.language;

import android.app.Application;
import android.os.AsyncTask;

import com.alexkn.syntact.dataaccess.common.AppDatabase;
import com.alexkn.syntact.dataaccess.util.Mapper;
import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.domain.repository.LanguagePairRepository;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.inject.Inject;

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
    public void delete(Long id) {

        languagePairDao.delete(id);
    }

    @Override
    public void update(LanguagePair languagePair) {

        languagePairDao.update(Mapper.toLanguagePairEntity(languagePair));
    }

    @Override
    public LanguagePair find(Long id) {

        return Mapper.toLanguagePair(languagePairDao.find(id));
    }

    @Override
    public boolean languagePairExists(Locale left, Locale right) {

        return languagePairDao.languagePairExists(left, right);
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

        return Transformations.map(languagePairDao.findLanguagePair(id), Mapper::toLanguagePair);
    }
}
