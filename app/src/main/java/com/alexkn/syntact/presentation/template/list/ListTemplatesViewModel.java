package com.alexkn.syntact.presentation.template.list;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.model.Template;
import com.alexkn.syntact.domain.usecase.CreateTemplate;
import com.alexkn.syntact.domain.usecase.ManageTemplates;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;

import java.util.List;

import javax.inject.Inject;

public class ListTemplatesViewModel extends AndroidViewModel {

    @Inject
    CreateTemplate createTemplate;

    @Inject
    ManageTemplates manageTemplates;

    private final LiveData<List<Template>> templates;

    public ListTemplatesViewModel(@NonNull Application application) {

        super(application);

        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);

        templates = manageTemplates.getTemplates();
    }

    public LiveData<List<Template>> getTemplates() {

        return templates;
    }

    public void refreshTemplates(){

        AsyncTask.execute(() -> manageTemplates.loadTemplates());
    }
}
