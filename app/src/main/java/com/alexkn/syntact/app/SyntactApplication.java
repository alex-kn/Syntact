package com.alexkn.syntact.app;

import android.app.Application;

public class SyntactApplication extends Application implements ApplicationComponentProvider {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {

        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder().applicationContext(this).build();
    }

    @Override
    public ApplicationComponent getApplicationComponent() {

        return applicationComponent;
    }
}
