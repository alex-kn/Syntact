package com.alexkn.syntact.domain;

import android.app.Application;

public class SyntactApplication extends Application {

    private SyntactComponent syntactComponent;

    @Override
    public void onCreate() {

        super.onCreate();
        syntactComponent = DaggerSyntactComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        }

    public SyntactComponent getSyntactComponent() {

        return syntactComponent;
    }
}
