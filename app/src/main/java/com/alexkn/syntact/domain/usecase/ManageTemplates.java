package com.alexkn.syntact.domain.usecase;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.alexkn.syntact.app.Property;
import com.alexkn.syntact.domain.model.Template;
import com.alexkn.syntact.domain.repository.TemplateRepository;
import com.alexkn.syntact.restservice.SyntactService;
import com.alexkn.syntact.restservice.TemplateResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class ManageTemplates {

    private static final String TAG = ManageTemplates.class.getSimpleName();

    @Inject
    SyntactService syntactService;

    @Inject
    Property property;

    @Inject
    TemplateRepository templateRepository;

    @Inject
    ManageTemplates() {}

    public void loadTemplates() {

        String token = "Token " + property.get("api-auth-token");

        syntactService.getTemplates(token).enqueue(new Callback<List<TemplateResponse>>() {

            @Override
            public void onResponse(Call<List<TemplateResponse>> call,
                    Response<List<TemplateResponse>> response) {

                AsyncTask.execute(() -> handleTemplateResponse(response));
            }

            @Override
            public void onFailure(Call<List<TemplateResponse>> call, Throwable t) {

                Log.e(TAG, "onFailure: Error receiving templates", t);
            }
        });
    }

    private void handleTemplateResponse(Response<List<TemplateResponse>> response) {

        if (response.isSuccessful()) {
            List<TemplateResponse> body = response.body();
            for (TemplateResponse templateResponse : body) {

                Template template = new Template();
                template.setId(templateResponse.getId());
                template.setName(templateResponse.getName());
                template.setTemplateType(templateResponse.getTemplateType());

                if (templateRepository.exists(template.getId())) {
                    templateRepository.update(template);
                } else {
                    templateRepository.insert(template);
                }
            }
        } else {
            Log.e(TAG, "onResponse: Error receiving templates");
        }
    }

    public LiveData<List<Template>> getTemplates(){

        return templateRepository.findAll();
    }
}
