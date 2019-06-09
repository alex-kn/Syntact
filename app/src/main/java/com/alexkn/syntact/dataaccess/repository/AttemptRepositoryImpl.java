package com.alexkn.syntact.dataaccess.repository;

import android.app.Application;

import com.alexkn.syntact.dataaccess.common.AppDatabase;
import com.alexkn.syntact.dataaccess.dao.AttemptDao;
import com.alexkn.syntact.domain.model.Attempt;
import com.alexkn.syntact.domain.repository.AttemptRepository;

import java.util.Collection;

import javax.inject.Inject;

public class AttemptRepositoryImpl implements AttemptRepository {

    private AttemptDao attemptDao;

    @Inject
    public AttemptRepositoryImpl(Application application) {

        AppDatabase database = AppDatabase.getDatabase(application);
        attemptDao = database.attemptDao();
    }

    @Override
    public Long insert(Attempt attempt) {

        return attemptDao.insert(attempt);
    }

    @Override
    public void insert(Collection<Attempt> t) {

        attemptDao.insert(t);
    }

    @Override
    public void update(Attempt attempt) {

        attemptDao.update(attempt);
    }

    @Override
    public void delete(Attempt attempt) {

        attemptDao.delete(attempt);
    }

    @Override
    public Attempt find(Long id) {

        return attemptDao.find(id);
    }

    @Override
    public void updateAttempt(Long solvableItemId, String attempt) {

        attemptDao.updateAttempt(solvableItemId, attempt);
    }
}
