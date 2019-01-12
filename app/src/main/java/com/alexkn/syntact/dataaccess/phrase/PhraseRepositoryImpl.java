package com.alexkn.syntact.dataaccess.phrase;

import android.app.Application;
import android.os.AsyncTask;

import com.alexkn.syntact.dataaccess.common.AppDatabase;
import com.alexkn.syntact.domain.repository.PhraseRepository;

import java.time.Instant;
import java.util.List;

import androidx.lifecycle.LiveData;

public class PhraseRepositoryImpl implements PhraseRepository {

    private PhraseDao phraseDao;

    private LiveData<List<PhraseEntity>> allPhrases;

    public PhraseRepositoryImpl(Application application) {

        AppDatabase database = AppDatabase.getDatabase(application);
        phraseDao = database.phraseDao();
        allPhrases = phraseDao.findAll();

    }

    @Override
    public void updateLastSolved(int id, Instant lastSolved) {

        AsyncTask.execute(()-> phraseDao.updateLastSolved(id, lastSolved));
    }

    @Override
    public LiveData<List<PhraseEntity>> getAllPhrases() {

        return allPhrases;
    }

    @Override
    public void insert(PhraseEntity phraseEntity) {

        AsyncTask.execute(() -> phraseDao.insert(phraseEntity));
    }

    @Override
    public void deleteAll(){
        AsyncTask.execute(() -> phraseDao.deleteAll());
    }
}