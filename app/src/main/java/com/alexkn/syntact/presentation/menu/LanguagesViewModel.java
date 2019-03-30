package com.alexkn.syntact.presentation.menu;

import android.app.Application;
import android.os.AsyncTask;
import android.view.View;

import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.domain.usecase.GeneratePhrasesUseCase;
import com.alexkn.syntact.domain.usecase.LanguageManagement;
import com.alexkn.syntact.domain.usecase.LanguageScoreManagement;
import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LanguagesViewModel extends AndroidViewModel {

    @Inject
    LanguageScoreManagement languageScoreManagement;

    @Inject
    LanguageManagement languageManagement;

    public LanguagesViewModel(@NonNull Application application) {

        super(application);

        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);
    }

    public LiveData<List<LanguagePair>> getLanguagePairs() {

        return languageManagement.getLanguagePairs();
    }

    public void populateData(View view) {

        AsyncTask.execute(() -> languageManagement.addLanguage(Locale.ITALIAN, Locale.FRENCH));
    }
}

