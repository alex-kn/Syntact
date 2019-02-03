package com.alexkn.syntact.presentation.app;

import android.app.Application;
import android.os.LocaleList;

import com.alexkn.syntact.domain.usecase.GenerateCharactersUseCase;

import java.util.Locale;

import javax.inject.Inject;

import androidx.core.os.ConfigurationCompat;

public class SyntactApplication extends Application implements ApplicationComponentProvider {

    private ApplicationComponent applicationComponent;


    @Override
    public void onCreate() {

        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

//        ConfigurationCompat.getLocales(getResources().getConfiguration()).get(0);

    }

    @Override
    public ApplicationComponent getApplicationComponent() {

        return applicationComponent;
    }
}
