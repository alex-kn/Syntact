package com.alexkn.syntact.dataaccess.language;

import android.app.Application;

import com.alexkn.syntact.dataaccess.common.AppDatabase;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;

public class ActiveLanguagePairRepositoryImpl {

    private ActiveLanguagePairDao activeLanguagePairDao;

    private LiveData<List<ActiveLanguagePairEntity>> activeLanguages;

    @Inject
    public ActiveLanguagePairRepositoryImpl(Application application) {

        AppDatabase database = AppDatabase.getDatabase(application);
        activeLanguagePairDao = database.activeLanguagePairDao();
        activeLanguages = activeLanguagePairDao.findAll();
    }

}
