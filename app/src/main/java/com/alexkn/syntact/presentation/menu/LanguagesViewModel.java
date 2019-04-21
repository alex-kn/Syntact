package com.alexkn.syntact.presentation.menu;

import android.app.Application;
import android.os.AsyncTask;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.domain.usecase.ManageLanguages;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LanguagesViewModel extends AndroidViewModel {

    private final LiveData<List<LanguagePair>> languagePairs;

    @Inject
    ManageLanguages manageLanguages;

    public LanguagesViewModel(@NonNull Application application) {

        super(application);

        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);

        languagePairs = manageLanguages.getLanguagePairs();
    }

    public void deleteLanguage(LanguagePair languagePair) {

        AsyncTask.execute(() -> manageLanguages.removeLanguage(languagePair.getId()));
    }

    LiveData<List<LanguagePair>> getLanguagePairs() {

        return languagePairs;
    }
}

