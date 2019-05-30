package com.alexkn.syntact.presentation.template.list;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.usecase.CreateTemplate;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;

import javax.inject.Inject;

public class TemplateCreateViewModel extends AndroidViewModel {

    @Inject
    CreateTemplate createTemplate;

    public TemplateCreateViewModel(@NonNull Application application) {

        super(application);
        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);

    }

    public void createTemplate(String name, String[] words) {

        AsyncTask.execute(() -> createTemplate.create(name, words));

    }
}
