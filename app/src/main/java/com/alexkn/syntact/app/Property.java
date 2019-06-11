package com.alexkn.syntact.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.alexkn.syntact.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Property {

    private static final String TAG = Property.class.getSimpleName();

    private Properties properties;

    @Inject
    public Property(Context application) {

        try {
            Resources resources = application.getResources();
            InputStream inputStream = resources.openRawResource(R.raw.config);

            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            Log.e(TAG, "loadProperties: Unable to open config file.", e);
        }
    }

    public String get(String key) {

        return properties.getProperty(key);
    }
}
