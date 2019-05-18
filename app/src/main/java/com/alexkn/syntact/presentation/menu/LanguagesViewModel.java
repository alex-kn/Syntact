package com.alexkn.syntact.presentation.menu;

import android.app.Application;
import android.os.AsyncTask;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.usecase.ManageLanguages;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LanguagesViewModel extends AndroidViewModel {

    private final LiveData<List<Bucket>> languagePairs;

    @Inject
    ManageLanguages manageLanguages;

    public LanguagesViewModel(@NonNull Application application) {

        super(application);

        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);

        AsyncTask.execute(() -> manageLanguages.addLanguage(Locale.GERMAN, Locale.CHINESE));
        languagePairs = manageLanguages.getLanguagePairs();
    }

    public void deleteLanguage(Bucket bucket) {

        AsyncTask.execute(() -> manageLanguages.removeLanguage(bucket.getId()));
    }

    LiveData<List<Bucket>> getLanguagePairs() {

        return languagePairs;
    }
}

