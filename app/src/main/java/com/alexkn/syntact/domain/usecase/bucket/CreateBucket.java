package com.alexkn.syntact.domain.usecase.bucket;

import android.os.AsyncTask;
import android.util.Log;

import com.alexkn.syntact.app.Property;
import com.alexkn.syntact.domain.common.GameMode;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.model.SolvableTranslation;
import com.alexkn.syntact.domain.repository.BucketRepository;
import com.alexkn.syntact.domain.usecase.play.ManageLetters;
import com.alexkn.syntact.domain.usecase.play.ManagePhrases;
import com.alexkn.syntact.domain.util.PhraseGenerator;
import com.alexkn.syntact.restservice.PhraseResponse;
import com.alexkn.syntact.restservice.SyntactService;
import com.alexkn.syntact.restservice.TemplateResponse;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class CreateBucket {

    private static final String TAG = CreateBucket.class.getSimpleName();

    @Inject
    BucketRepository bucketRepository;

    @Inject
    ManageLetters manageLetters;

    @Inject
    ManagePhrases managePhrases;

    @Inject
    Property property;

    @Inject
    SyntactService syntactService;

    @Inject
    PhraseGenerator phraseGenerator;

    @Inject
    CreateBucket() {}

    public void checkBucketReadiness(Long bucketid, Bucket bucket) {

        String token = "Token " + property.get("api-auth-token");
        String targetlang = bucket.getLanguage().getLanguage();
        String srcLang = bucket.getUserLanguage().getLanguage();
        Integer templateId = bucket.getTemplateId();

        try {
            Response<List<PhraseResponse>> response = syntactService
                    .getPhrases(token, srcLang, targetlang, templateId).execute();
            if (response.isSuccessful()) {
                List<PhraseResponse> body = response.body();

                if (body.stream().anyMatch(resp -> resp.getTranslations().isEmpty())) {
                    Log.i(TAG, "Translations not yet ready");
                }

                List<SolvableTranslation> solvabeItems = phraseGenerator
                        .createSolvabeItems(bucketid, body);

                Log.i(TAG, body.size() + " phrases fetched with " + solvabeItems.size() +
                        " translations");

                bucket.setReady(true);
                AsyncTask.execute(() -> {
                    bucketRepository.update(bucket);
                    managePhrases.saveSolvableItems(solvabeItems);
                });

                Log.i(TAG, "onResponse: " + solvabeItems.size() + " new solvable items");
            } else {
                Log.e(TAG, "onResponse: Error receiving phrases");
            }
        } catch (IOException e) {
            Log.e(TAG, "checkBucketReadiness: ", e);
        }
    }

    public void addBucket(Locale language, int templateId) {

        Locale sourceLanguage = Locale.getDefault();

        Bucket bucket = new Bucket();
        bucket.setStreak(0);
        bucket.setCreated(Instant.now());
        bucket.setLanguage(language);
        bucket.setUserLanguage(sourceLanguage);
        bucket.setTemplateId(templateId);
        bucket.setReady(false);
        bucket.setGameMode(GameMode.DRAG);
        bucket.setTotalSolvedCount(0);

        Long bucketId = bucketRepository.insert(bucket);
        manageLetters.initializeLetters(bucketId);

        String token = "Token " + property.get("api-auth-token");
        String targetlang = language.getLanguage();
        String srcLang = sourceLanguage.getLanguage();

        try {
            Response<ResponseBody> response = syntactService
                    .translateTemplate(token, language.getLanguage(), templateId).execute();
            if (response.isSuccessful()) {

                Log.i(TAG, "Translation complete");
                checkBucketReadiness(bucketId, bucket);
            }
        } catch (IOException e) {
            Log.e(TAG, "addBucket: ", e);
        }
    }
}
