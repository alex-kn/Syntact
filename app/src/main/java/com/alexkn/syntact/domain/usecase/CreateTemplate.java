package com.alexkn.syntact.domain.usecase;

import android.util.Log;

import com.alexkn.syntact.app.Property;
import com.alexkn.syntact.domain.common.TemplateType;
import com.alexkn.syntact.domain.model.Template;
import com.alexkn.syntact.domain.repository.TemplateRepository;
import com.alexkn.syntact.restservice.PhraseResponse;
import com.alexkn.syntact.restservice.SyntactService;
import com.alexkn.syntact.restservice.TemplateResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class CreateTemplate {

    private static final String TAG = CreateTemplate.class.getSimpleName();

    @Inject
    Property property;

    @Inject
    SyntactService syntactService;

    @Inject
    TemplateRepository templateRepository;

    @Inject
    CreateTemplate() {}

    public void createFromPhoto() {

    }

    public void create(String name, String[] words) {

        String token = "Token " + property.get("api-auth-token");

        ArrayList<PhraseResponse> phrases = new ArrayList<>();

        for (String word : words) {
            PhraseResponse phrase = new PhraseResponse();
            phrase.setLanguage(new Locale(Locale.getDefault().getLanguage()));
            phrase.setText(word);
            phrases.add(phrase);
        }

        TemplateResponse template = new TemplateResponse();
        template.setTemplateType(TemplateType.CUSTOM);
        template.setName(name);
        template.setPhrases(phrases);
        template.setLanguage(new Locale(Locale.getDefault().getLanguage()));

        Log.i(TAG, "Creating new template " + name + " with " + words.length + " words");
        syntactService.postTemplate(token, template)
                .enqueue(new Callback<List<TemplateResponse>>() {

                    @Override
                    public void onResponse(Call<List<TemplateResponse>> call,
                            Response<List<TemplateResponse>> response) {

                        if (!response.isSuccessful()) {
                            List<TemplateResponse> body = response.body();
                            Log.i(TAG, "Creation of template " + name + " successful");
                            ArrayList<Template> templates = new ArrayList<>();
                            for (TemplateResponse templateResponse : body) {
                                Template entity = new Template();
                                entity.setName(templateResponse.getName());
                                entity.setTemplateType(template.getTemplateType());
                                entity.setId(templateResponse.getId());
                                templates.add(entity);
                            }
                            templateRepository.insert(templates);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TemplateResponse>> call, Throwable t) {

                    }
                });
    }
}
