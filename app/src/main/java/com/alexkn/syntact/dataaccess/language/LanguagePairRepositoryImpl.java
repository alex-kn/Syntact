package com.alexkn.syntact.dataaccess.language;

import android.app.Application;
import android.os.AsyncTask;

import com.alexkn.syntact.dataaccess.common.AppDatabase;
import com.alexkn.syntact.dataaccess.util.Mapper;
import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.domain.repository.LanguagePairRepository;
import com.alexkn.syntact.presentation.menu.LanguagesViewModel;

import java.util.List;
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
    public void insert(LanguagePair languagePair) {

        AsyncTask.execute(() -> languagePairDao
                .insert(Mapper.toLanguagePairEntity(languagePair)));
    }

    @Override
    public LiveData<List<LanguagePair>> findAllLanguagePairs() {

        return Transformations.map(languagePairDao.findAll(),
                input -> input.stream().map(Mapper::toLanguagePair)
                        .collect(Collectors.toList()));
    }

    @Override
    public void updateScore(int id, int newScore) {

        AsyncTask.execute(() -> languagePairDao.updateScore(id, newScore));
    }
}
