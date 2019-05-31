package com.alexkn.syntact.presentation.template.create;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.usecase.CreateTemplate;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;

import javax.inject.Inject;

public class CreateTemplateViewModel extends AndroidViewModel {

    @Inject
    CreateTemplate createTemplate;

    public CreateTemplateViewModel(@NonNull Application application) {

        super(application);
        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);
    }

    public void createTemplate(String name, String[] words) {

        AsyncTask.execute(() -> createTemplate.create(name, words));
    }
}
