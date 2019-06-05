package com.alexkn.syntact.dataaccess.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alexkn.syntact.dataaccess.common.AppDatabase;
import com.alexkn.syntact.dataaccess.dao.SolvableItemDao;
import com.alexkn.syntact.domain.model.SolvableItem;
import com.alexkn.syntact.domain.model.cto.SolvableTranslationCto;
import com.alexkn.syntact.domain.repository.SolvableItemRepository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class SolvableItemRepositoryImpl implements SolvableItemRepository {

    private SolvableItemDao solvableItemDao;

    @Inject
    SolvableItemRepositoryImpl(Application application) {

        AppDatabase database = AppDatabase.getDatabase(application);
        solvableItemDao = database.solvableItemDao();
    }

    @Override
    public void update(SolvableItem solvableTranslation) {

        solvableItemDao.update(solvableTranslation);
    }

    @Override
    public void delete(SolvableItem solvableItem) {

        solvableItemDao.delete(solvableItem);
    }

    @Override
    public Long insert(SolvableItem solvableTranslation) {

        return solvableItemDao.insert(solvableTranslation);
    }

    @Override
    public void insert(Collection<SolvableItem> solvableTranslations) {

        solvableItemDao.insert(solvableTranslations);
    }

    @Override
    public LiveData<List<SolvableTranslationCto>> getSolvableTranslationsDueBefore(Long bucketId,
            Instant time) {

        return solvableItemDao.getSolvableTranslationsDueBefore(bucketId, time);
    }
}
