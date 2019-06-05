package com.alexkn.syntact.dataaccess.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alexkn.syntact.dataaccess.common.AppDatabase;
import com.alexkn.syntact.dataaccess.dao.LetterDao;
import com.alexkn.syntact.domain.common.LetterColumn;
import com.alexkn.syntact.domain.model.Letter;
import com.alexkn.syntact.domain.repository.LetterRepository;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class LetterRepositoryImpl implements LetterRepository {

    private LetterDao letterDao;

    @Inject
    public LetterRepositoryImpl(Application application) {

        AppDatabase database = AppDatabase.getDatabase(application);
        this.letterDao = database.letterDao();
    }

    @Override
    public LiveData<List<Letter>> find(Long bucketId, LetterColumn letterColumn) {

        return letterDao.find(bucketId, letterColumn);
    }

    @Override
    public void insert(Collection<Letter> letters) {

        letterDao.insert(letters);
    }

    @Override
    public void update(Letter letter) {

        letterDao.update(letter);
    }

    @Override
    public Long insert(Letter letter) {

        return letterDao.insert(letter);
    }

    @Override
    public void delete(Letter letter) {

        letterDao.delete(letter);
    }

    @Override
    public void deleteAllLettersForLanguage(Long bucketId) {

        letterDao.deleteAllLettersForLanguage(bucketId);
    }
}
