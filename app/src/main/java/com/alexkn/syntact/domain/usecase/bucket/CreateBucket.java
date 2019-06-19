package com.alexkn.syntact.domain.usecase.bucket;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.alexkn.syntact.app.Property;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.repository.AttemptRepository;
import com.alexkn.syntact.domain.repository.BucketRepository;
import com.alexkn.syntact.domain.repository.ClueRepository;
import com.alexkn.syntact.domain.repository.SolvableItemRepository;
import com.alexkn.syntact.domain.usecase.play.ManageLetters;
import com.alexkn.syntact.domain.usecase.play.ManageSolvableItems;
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
    Context application;

    @Inject
    CreateBucket() {}

    public LiveData<List<Template>> findAvailableTemplates() {

        MutableLiveData<List<Template>> templates = new MutableLiveData<>();
        String token = "Token " + property.get("api-auth-token");
        syntactService.getTemplates(token).enqueue(new Callback<List<Template>>() {

            @Override
            public void onResponse(@NonNull Call<List<Template>> call, @NonNull Response<List<Template>> response) {

                if (response.isSuccessful()) {
                    Log.i(TAG, "Found " + response.body().size() + " templates");
                    templates.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Template>> call, @NonNull Throwable t) {

            }
        });
        return templates;
    }

    public void addBucketWithNewTemplate(String name, Locale language, List<String> words) {

        Data data = new Data.Builder().putString("name", name).putString("language", language.getLanguage())
                .putStringArray("words", words.toArray(new String[0])).build();

        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(CreateBucketWorker.class).setInputData(data).build();
        WorkManager.getInstance().enqueueUniqueWork(CreateBucketWorker.class.getName(), ExistingWorkPolicy.KEEP, workRequest);
    }

    public void addBucketWithExistingTemplate(Locale language, Template template) {

        Locale sourceLanguage = Locale.getDefault();

        Bucket bucket = new Bucket();
        bucket.setCreatedAt(Instant.now());
        bucket.setLanguage(language);
        bucket.setUserLanguage(sourceLanguage);
        bucket.setPhrasesUrl(template.getPhrasesUrl());
        bucket.setItemCount(template.getCount());
        Long bucketId = bucketRepository.insert(bucket);

        String token = "Token " + property.get("api-auth-token");

        manageLetters.initializeLetters(bucketId);
    }
}
