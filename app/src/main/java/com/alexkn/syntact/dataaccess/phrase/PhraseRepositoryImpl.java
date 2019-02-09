package com.alexkn.syntact.dataaccess.phrase;

import android.app.Application;
import android.os.AsyncTask;

import com.alexkn.syntact.dataaccess.common.AppDatabase;
import com.alexkn.syntact.dataaccess.util.Mapper;
import com.alexkn.syntact.domain.model.Phrase;
import com.alexkn.syntact.domain.repository.PhraseRepository;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

@Singleton
public class PhraseRepositoryImpl implements PhraseRepository {

    private PhraseDao phraseDao;

    private LiveData<List<PhraseEntity>> phrases;

    @Inject
    PhraseRepositoryImpl(Application application) {

        //TODO pass locale
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
    public LiveData<List<Phrase>> getAllPhrases(Locale locale) {
        return Transformations.map(phraseDao.findAll(locale),
                input -> input.stream().map(Mapper::toPhrase).collect(Collectors.toList()));
    }

    @Override
    public void insert(Phrase phrase) {

        AsyncTask.execute(() -> phraseDao.insert(Mapper.toPhraseEntity(phrase)));
    }

    @Override
    public void insert(List<Phrase> phrases) {

        AsyncTask.execute(() -> phraseDao.insert(Mapper.toPhraseEntity(phrases)));
    }

    @Override
    public void deleteAll() {

        AsyncTask.execute(() -> phraseDao.deleteAll());
    }

    @Override
    public int count() {

        return phraseDao.count();
    }
}
