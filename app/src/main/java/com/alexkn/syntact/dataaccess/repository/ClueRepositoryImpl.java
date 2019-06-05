package com.alexkn.syntact.dataaccess.repository;

import android.app.Application;

import com.alexkn.syntact.dataaccess.common.AppDatabase;
import com.alexkn.syntact.dataaccess.dao.ClueDao;
import com.alexkn.syntact.domain.model.Clue;
import com.alexkn.syntact.domain.repository.ClueRepository;

import java.util.Collection;

import javax.inject.Inject;

public class ClueRepositoryImpl implements ClueRepository {

    private ClueDao clueDao;

    @Inject
    public ClueRepositoryImpl(Application application) {

        AppDatabase database = AppDatabase.getDatabase(application);
        clueDao = database.clueDao();
    }

    @Override
    public Long insert(Clue clue) {

        return clueDao.insert(clue);
    }

    @Override
    public void insert(Collection<Clue> t) {

        clueDao.insert(t);
    }

    @Override
    public void update(Clue clue) {

        clueDao.update(clue);
    }

    @Override
    public void delete(Clue clue) {

        clueDao.delete(clue);
    }
}
