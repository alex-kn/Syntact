package com.alexkn.syntact.hangwords.dataaccess.impl;

import android.app.Application;
import android.os.AsyncTask;

import com.alexkn.syntact.hangwords.dataaccess.api.PhraseDao;
import com.alexkn.syntact.hangwords.dataaccess.api.to.Phrase;

import java.time.Instant;
import java.util.List;

import androidx.lifecycle.LiveData;

public class PhraseRepository {

    private PhraseDao phraseDao;

    private LiveData<List<Phrase>> allPhrases;

    public PhraseRepository(Application application) {

        AppDatabase database = AppDatabase.getDatabase(application);
        phraseDao = database.phraseDao();
        allPhrases = phraseDao.findAll();

    }

    public void updateLastSolved(int id, Instant lastSolved) {

        AsyncTask.execute(()-> phraseDao.updateLastSolved(id, lastSolved));
    }

    public LiveData<List<Phrase>> getAllPhrases() {

        return allPhrases;
    }

    public void insert(Phrase phrase) {

        AsyncTask.execute(() -> phraseDao.insert(phrase));
    }

    public void deleteAll(){
        AsyncTask.execute(() -> phraseDao.deleteAll());
    }
}
