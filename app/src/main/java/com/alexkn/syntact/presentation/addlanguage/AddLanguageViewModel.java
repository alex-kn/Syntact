package com.alexkn.syntact.presentation.addlanguage;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.usecase.CreateBucket;
import com.alexkn.syntact.domain.usecase.ManageBuckets;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class AddLanguageViewModel extends AndroidViewModel {

    private final List<Locale> availableBuckets;

    @Inject
    ManageBuckets manageBuckets;

    @Inject
    CreateBucket createBucket;

    public AddLanguageViewModel(@NonNull Application application) {

        super(application);

        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);

        availableBuckets = manageBuckets.getAvailableBuckets();
    }


    void addLanguage(Locale language) {

        AsyncTask.execute(() -> createBucket.addBucket(language));
    }

    public List<Locale> getAvailableBuckets() {

        return availableBuckets;
    }
}
