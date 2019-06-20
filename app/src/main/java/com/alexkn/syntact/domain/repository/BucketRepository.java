package com.alexkn.syntact.domain.repository;

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
import com.alexkn.syntact.data.common.AppDatabase;
import com.alexkn.syntact.data.dao.BucketDao;
import com.alexkn.syntact.data.model.Bucket;
import com.alexkn.syntact.data.model.views.BucketDetail;
import com.alexkn.syntact.domain.worker.CreateBucketWorker;
import com.alexkn.syntact.restservice.SyntactService;
import com.alexkn.syntact.restservice.Template;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class BucketRepository {

    private static final String TAG = BucketRepository.class.getSimpleName();

    private final BucketDao bucketDao;

    private final SyntactService syntactService;

    private Property property;

    @Inject
    BucketRepository(SyntactService syntactService, Property property, Context context) {

        this.syntactService = syntactService;
        this.property = property;
        bucketDao = AppDatabase.Companion.getDatabase(context).bucketDao();
    }

    public List<Locale> getAvailableLanguages() {

        String[] languages = property.get("available-languages").split(",");
        return Arrays.stream(languages).map(Locale::new).collect(Collectors.toList());
    }

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
        bucketDao.insert(bucket);
    }

    public void removeLanguage(Bucket bucket) {

        bucketDao.delete(bucket);
    }

    public LiveData<Bucket> getBucket(Long id) {

        return bucketDao.findBucket(id);
    }

    public LiveData<List<BucketDetail>> getBucketDetails() {

        return bucketDao.findBucketDetails();
    }
}
