package com.alexkn.syntact.domain.usecase.bucket;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alexkn.syntact.app.Property;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.model.SolvableItem;
import com.alexkn.syntact.domain.repository.AttemptRepository;
import com.alexkn.syntact.domain.repository.BucketRepository;
import com.alexkn.syntact.domain.repository.ClueRepository;
import com.alexkn.syntact.domain.repository.SolvableItemRepository;
import com.alexkn.syntact.domain.usecase.play.ManageLetters;
import com.alexkn.syntact.domain.usecase.play.ManageSolvableItems;
import com.alexkn.syntact.restservice.Phrase;
import com.alexkn.syntact.restservice.SyntactService;
import com.alexkn.syntact.restservice.Template;

import java.time.Instant;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class CreateBucket {

    private static final String TAG = CreateBucket.class.getSimpleName();

    @Inject
    BucketRepository bucketRepository;

    @Inject
    AttemptRepository attemptRepository;

    @Inject
    ClueRepository clueRepository;

    @Inject
    SolvableItemRepository solvableItemRepository;

    @Inject
    ManageLetters manageLetters;

    @Inject
    ManageSolvableItems manageSolvableItems;

    @Inject
    Property property;

    @Inject
    SyntactService syntactService;

    @Inject
    Application application;

    @Inject
    CreateBucket() {}

    public LiveData<List<Template>> findAvailableTemplates() {

        MutableLiveData<List<Template>> templates = new MutableLiveData<>();
        String token = "Token " + property.get("api-auth-token");
        syntactService.getTemplates(token).enqueue(new Callback<List<Template>>() {

            @Override
            public void onResponse(@NonNull Call<List<Template>> call,
                    @NonNull Response<List<Template>> response) {

                if (response.isSuccessful()) {
                    templates.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Template>> call, @NonNull Throwable t) {

            }
        });
        return templates;
    }

    public void addBucket(Locale language, Template template) {

        Locale sourceLanguage = Locale.getDefault();

        Bucket bucket = new Bucket();
        bucket.setCreatedAt(Instant.now());
        bucket.setLanguage(language);
        bucket.setUserLanguage(sourceLanguage);
        bucket.setPhrasesUrl(template.getPhrasesUrl());
        Long bucketId = bucketRepository.insert(bucket);

        String token = "Token " + property.get("api-auth-token");


        manageLetters.initializeLetters(bucketId);
    }

    public void createBucket(Locale language, String[] words) {

    }
}
