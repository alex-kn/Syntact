package com.alexkn.syntact.domain.usecase;

import android.app.Application;
import android.content.Context;

import com.alexkn.syntact.R;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

@Singleton
public class ActiveLanguages {

    MutableLiveData<List<Locale>> activeLanguages = new MutableLiveData<>();

    @Inject

    public ActiveLanguages(Application application) {

        application.getSharedPreferences(application.getString(R.string.active_languages_file_key),
                Context.MODE_PRIVATE);
    }

    public void addActiveLanguage(Locale language) {

    }

    public void removeActiveLanguage(Locale language) {

    }

    public LiveData<List<Locale>> getActiveLanguages() {

        return activeLanguages;
    }
}
