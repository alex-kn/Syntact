package com.alexkn.syntact.dataaccess.letter;

import android.app.Application;
import android.os.AsyncTask;

import com.alexkn.syntact.dataaccess.common.AppDatabase;
import com.alexkn.syntact.dataaccess.util.Mapper;
import com.alexkn.syntact.domain.common.LetterColumn;
import com.alexkn.syntact.domain.model.Letter;
import com.alexkn.syntact.domain.repository.LetterRepository;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

public class LetterRepositoryImpl implements LetterRepository {

    private LetterDao letterDao;

    @Inject
    public LetterRepositoryImpl(Application application) {

        AppDatabase database = AppDatabase.getDatabase(application);
        this.letterDao = database.letterDao();
    }

    @Override
    public LiveData<List<Letter>> find(LetterColumn letterColumn) {

        return Transformations.map(letterDao.find(letterColumn),
                input -> input.stream().map(Mapper::toLetter).collect(Collectors.toList()));
    }

    @Override
    public void insert(List<Letter> letters) {

        AsyncTask.execute(() -> letterDao.insert(Mapper.toLetterEntity(letters)));
    }

    @Override
    public void insert(Letter letter) {

        AsyncTask.execute(() -> letterDao.insert(Mapper.toLetterEntity(letter)));

    }

    @Override
    public void delete(Letter letter) {

        AsyncTask.execute(() -> letterDao.delete(Mapper.toLetterEntity(letter)));

    }
}
