package com.alexkn.syntact.presentation.app;

import android.app.Application;

import com.alexkn.syntact.domain.usecase.GenerateCharactersUseCase;

import javax.inject.Inject;

public class SyntactApplication extends Application implements ApplicationComponentProvider {

    private ApplicationComponent applicationComponent;


    @Override
    public void onCreate() {

        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
    }

    @Override
    public ApplicationComponent getApplicationComponent() {

        return applicationComponent;
    }
}
