package com.alexkn.syntact.presentation.addlanguage;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.usecase.ManageLanguages;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;

import java.util.Locale;

import javax.inject.Inject;

public class AddLanguageViewModel extends AndroidViewModel {

    @Inject
    ManageLanguages manageLanguages;

    public AddLanguageViewModel(@NonNull Application application) {

        super(application);

        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);
    }

    void addLanguage(Locale languageLeft, Locale languageRight) {

        AsyncTask.execute(() -> manageLanguages.addLanguage(languageLeft, languageRight));
    }
}
