package com.alexkn.syntact.presentation.menu;

import android.app.Application;
import android.os.AsyncTask;
import android.view.View;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.domain.usecase.ManageLanguages;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LanguagesViewModel extends AndroidViewModel {

    @Inject
    ManageLanguages manageLanguages;

    public LanguagesViewModel(@NonNull Application application) {

        super(application);

        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);
    }

    public LiveData<List<LanguagePair>> getLanguagePairs() {

        return manageLanguages.getLanguagePairs();
    }

    public void populateData(View view) {

        AsyncTask.execute(() -> manageLanguages.addLanguage(Locale.ENGLISH, Locale.GERMAN));
    }
}

