package com.alexkn.syntact.domain.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.app.Property;
import com.alexkn.syntact.data.common.AppDatabase;
import com.alexkn.syntact.data.dao.BucketDao;
import com.alexkn.syntact.restservice.TemplateType;
import com.alexkn.syntact.data.model.Bucket;
import com.alexkn.syntact.restservice.PhrasesRequest;
import com.alexkn.syntact.restservice.SyntactService;
import com.alexkn.syntact.restservice.Template;
import com.alexkn.syntact.restservice.TemplateRequest;

import java.io.IOException;
import java.time.Instant;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class CreateBucketWorker extends Worker {

    private static final String TAG = CreateBucketWorker.class.getSimpleName();

    private final BucketDao bucketDao;

    @Inject
    Property property;

    @Inject
    SyntactService syntactService;

    public CreateBucketWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {

        super(context, workerParams);
        ((ApplicationComponentProvider) context).getApplicationComponent().inject(this);

        bucketDao = AppDatabase.Companion.getDatabase(context).bucketDao();
    }

    @NonNull
    @Override
    public Result doWork() {

        Data inputData = getInputData();

        String name = inputData.getString("name");
        Locale language = new Locale(inputData.getString("language"));
        String[] words = inputData.getStringArray("words");

        String token = "Token " + property.get("api-auth-token");

        TemplateRequest templateRequest = new TemplateRequest();
        templateRequest.setLanguage(language);
        templateRequest.setName(name);
        templateRequest.setTemplateType(TemplateType.CUSTOM);
        try {
            Response<Template> templateResponse = syntactService.postTemplate(token, templateRequest).execute();
            if (templateResponse.isSuccessful()) {
                Template template = templateResponse.body();
                Bucket bucket = new Bucket();
                bucket.setPhrasesUrl(template.getPhrasesUrl());
                bucket.setCreatedAt(Instant.now());
                bucket.setId(Long.valueOf(template.getId()));
                bucket.setItemCount(template.getCount());
                bucket.setLanguage(template.getLanguage());
                bucket.setUserLanguage(Locale.getDefault());
                Log.i(TAG, "New bucket created");

                Response<ResponseBody> phraseResponse = syntactService.postPhrases(token, bucket.getId(), Locale.getDefault().getLanguage(),
                        Stream.of(words).map(s -> new PhrasesRequest(s, language)).collect(Collectors.toList())).execute();

                if (phraseResponse.isSuccessful()) {
                    Log.i(TAG, "New phrases created");
                    bucket.setItemCount(words.length);
                    bucketDao.insert(bucket);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error creating Bucket", e);
            return Result.failure();
        }

        return Result.success();
    }
}
