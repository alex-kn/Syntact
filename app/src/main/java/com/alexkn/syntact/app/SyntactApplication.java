package com.alexkn.syntact.app;

import android.app.Application;


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
