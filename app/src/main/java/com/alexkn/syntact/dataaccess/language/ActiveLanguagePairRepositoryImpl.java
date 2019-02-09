package com.alexkn.syntact.dataaccess.language;

import android.app.Application;
import android.os.AsyncTask;

import com.alexkn.syntact.dataaccess.common.AppDatabase;
import com.alexkn.syntact.dataaccess.util.Mapper;
import com.alexkn.syntact.domain.model.ActiveLanguagePair;
import com.alexkn.syntact.domain.repository.ActiveLanguagePairRepository;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

public class ActiveLanguagePairRepositoryImpl implements ActiveLanguagePairRepository {

    private ActiveLanguagePairDao activeLanguagePairDao;

    @Inject
    ActiveLanguagePairRepositoryImpl(Application application) {

        AppDatabase database = AppDatabase.getDatabase(application);
        activeLanguagePairDao = database.activeLanguagePairDao();
    }

    @Override
    public void insert(ActiveLanguagePair activeLanguagePair) {

        AsyncTask.execute(() -> activeLanguagePairDao
                .insert(Mapper.toActiveLanguagePairEntity(activeLanguagePair)));
    }

    @Override
    public LiveData<List<ActiveLanguagePair>> findAllActiveLanguagePairs() {

        return Transformations.map(activeLanguagePairDao.findAll(),
                input -> input.stream().map(Mapper::toActiveLanguagePair)
                        .collect(Collectors.toList()));
    }
}
