package com.alexkn.syntact.dataaccess.phrase;

import android.app.Application;
import android.os.AsyncTask;

import com.alexkn.syntact.dataaccess.common.AppDatabase;
import com.alexkn.syntact.dataaccess.util.Mapper;
import com.alexkn.syntact.domain.model.Phrase;
import com.alexkn.syntact.domain.repository.PhraseRepository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

@Singleton
public class PhraseRepositoryImpl implements PhraseRepository {

    private PhraseDao phraseDao;

    private LiveData<List<PhraseEntity>> phrases;

    @Inject
    public PhraseRepositoryImpl(Application application) {

        AppDatabase database = AppDatabase.getDatabase(application);
        phraseDao = database.phraseDao();
        phrases = phraseDao.findAll();
    }

    @Override
    public void updateLastSolved(int id, Instant lastSolved) {

        AsyncTask.execute(() -> phraseDao.updateLastSolved(id, lastSolved));
    }

    @Override
    public void updateAttempt(int id, String attempt) {

        AsyncTask.execute(() -> phraseDao.updateAttempt(id, attempt));

    }

    @Override
    public LiveData<List<Phrase>> getAllPhrases() {
        return Transformations.map(phraseDao.findAll(),
                input -> input.stream().map(Mapper::toPhrase).collect(Collectors.toList()));
    }

    @Override
    public void insert(PhraseEntity phraseEntity) {

        AsyncTask.execute(() -> phraseDao.insert(phraseEntity));
    }

    @Override
    public void deleteAll() {

        AsyncTask.execute(() -> phraseDao.deleteAll());
    }
}
