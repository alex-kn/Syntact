package com.alexkn.syntact.presentation.addlanguage;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.usecase.ManageLanguages;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class AddLanguageViewModel extends AndroidViewModel {

    private final List<Locale> availableLanguagePairs;

    @Inject
    ManageLanguages manageLanguages;

    private MutableLiveData<List<Locale>> availableLocalesLeft = new MutableLiveData<>();

    private MutableLiveData<List<Locale>> availableLocalesRight = new MutableLiveData<>();

    public AddLanguageViewModel(@NonNull Application application) {

        super(application);

        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);

        availableLanguagePairs = manageLanguages.getAvailableLanguagePairs();


    }

    void addLanguage(Locale languageLeft, Locale languageRight) {

        AsyncTask.execute(() -> manageLanguages.addLanguage(languageLeft, languageRight));
    }

    public List<Locale> getAvailableLanguagePairs() {

        return availableLanguagePairs;
    }
}
