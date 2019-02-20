package com.alexkn.syntact.presentation.menu;

import android.app.Application;

import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.domain.usecase.LanguageManagement;
import com.alexkn.syntact.domain.usecase.LanguageScoreManagement;
import com.alexkn.syntact.presentation.app.ApplicationComponentProvider;
import com.alexkn.syntact.presentation.view.DaggerViewComponent;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LanguagesViewModel extends AndroidViewModel {

    @Inject public LanguageScoreManagement languageScoreManagement;

    @Inject public LanguageManagement languageManagement;

    public LanguagesViewModel(@NonNull Application application) {

        super(application);

        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);


    }

    public LiveData<List<LanguagePair>> getLanguagePairs() {

        return languageManagement.getLanguagePairs();
    }

}

