package com.alexkn.syntact.dataaccess.letter;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.alexkn.syntact.dataaccess.common.AppDatabase;
import com.alexkn.syntact.domain.common.LetterColumn;
import com.alexkn.syntact.domain.model.Letter;
import com.alexkn.syntact.domain.repository.LetterRepository;

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
    public LiveData<List<Letter>> find(Long languagePairId, LetterColumn letterColumn) {

        return letterDao.find(languagePairId, letterColumn);
    }

    @Override
    public void insert(List<Letter> letters) {

        AsyncTask.execute(() -> letterDao.insert(letters));
    }

    @Override
    public void insert(Letter letter) {

        AsyncTask.execute(() -> letterDao.insert(letter));

    }

    @Override
    public void delete(Letter letter) {

        AsyncTask.execute(() -> letterDao.delete(letter));

    }

    @Override
    public void deleteAllLettersForLanguage(Long languagePairId) {
        letterDao.deleteAllLettersForLanguage(languagePairId);
    }
}
