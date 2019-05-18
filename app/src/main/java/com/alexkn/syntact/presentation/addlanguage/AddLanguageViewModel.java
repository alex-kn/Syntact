package com.alexkn.syntact.presentation.addlanguage;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.usecase.ManageBuckets;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class AddLanguageViewModel extends AndroidViewModel {

    private final List<Locale> availableBuckets;

    @Inject
    ManageBuckets manageBuckets;

    private MutableLiveData<List<Locale>> availableLocalesLeft = new MutableLiveData<>();

    private MutableLiveData<List<Locale>> availableLocalesRight = new MutableLiveData<>();

    public AddLanguageViewModel(@NonNull Application application) {

        super(application);

        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);

        availableBuckets = manageBuckets.getAvailableBuckets();


    }

    void addLanguage(Locale languageLeft, Locale languageRight) {

        AsyncTask.execute(() -> manageBuckets.addBucket(languageLeft, languageRight));
    }

    public List<Locale> getAvailableBuckets() {

        return availableBuckets;
    }
}
