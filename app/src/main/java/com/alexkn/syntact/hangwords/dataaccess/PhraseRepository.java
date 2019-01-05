package com.alexkn.syntact.hangwords.dataaccess;

import android.app.Application;
import android.os.AsyncTask;

import com.alexkn.syntact.hangwords.dataaccess.api.to.Phrase;
import com.alexkn.syntact.hangwords.dataaccess.api.PhraseDao;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;

public class PhraseRepository {

    private PhraseDao phraseDao;

    private LiveData<List<Phrase>> allPhrases;

    public PhraseRepository(Application application) {

        AppDatabase database = AppDatabase.getDatabase(application);
        phraseDao = database.phraseDao();
        allPhrases = phraseDao.findAll();

        insert(new Phrase("AB", "A"));
        insert(new Phrase("Aktion", "Action"));
        insert(new Phrase("Clown", "Clown"));
        insert(new Phrase("Owen", "Owen"));
        insert(new Phrase("Lewis", "Lewis"));
        insert(new Phrase("Bier", "Beer"));
        insert(new Phrase("Tee", "Tea"));
        insert(new Phrase("Alle", "All"));
        insert(new Phrase("Wackeln", "Wiggle"));
        insert(new Phrase("Warten", "Wait"));
        insert(new Phrase("Niedrig", "Low"));
        insert(new Phrase("Zeichentrickfilm", "Cartoon"));
    }

    public LiveData<List<Phrase>> getAllPhrases() {

        return allPhrases;
    }

    public void insert(Phrase phrase) {

        new InsertAsyncTask(phraseDao).execute(phrase);
    }

    private static class InsertAsyncTask extends AsyncTask<Phrase, Void, Void>{

        private PhraseDao mAsyncTaskDao;

        InsertAsyncTask(PhraseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Phrase... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
