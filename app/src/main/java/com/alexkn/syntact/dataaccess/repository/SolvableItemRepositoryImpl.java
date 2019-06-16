package com.alexkn.syntact.dataaccess.repository;

import android.content.Context;

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

import io.reactivex.Maybe;

public class SolvableItemRepositoryImpl implements SolvableItemRepository {

    private SolvableItemDao solvableItemDao;

    @Inject
    SolvableItemRepositoryImpl(Context application) {

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
    public SolvableItem find(Long id) {

        return solvableItemDao.find(id);
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

    @Override
    public Long getMaxId(Long bucketId) {
        return solvableItemDao.getMaxId(bucketId);
    }

    @Override
    public Maybe<SolvableTranslationCto[]> getNextTranslationDueBefore(long bucketId, Instant time, int count){

        return solvableItemDao.getNextTranslationDueBefore(bucketId, time, count);
    }
}
