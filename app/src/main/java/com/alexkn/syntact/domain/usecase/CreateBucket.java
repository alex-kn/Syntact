package com.alexkn.syntact.domain.usecase;

import android.util.Log;

import com.alexkn.syntact.app.Property;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.model.SolvableTranslation;
import com.alexkn.syntact.domain.model.Template;
import com.alexkn.syntact.domain.repository.BucketRepository;
import com.alexkn.syntact.domain.repository.TemplateRepository;
import com.alexkn.syntact.domain.util.PhraseGenerator;
import com.alexkn.syntact.restservice.PhraseResponse;
import com.alexkn.syntact.restservice.SyntactService;
import com.alexkn.syntact.restservice.TemplateResponse;

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


    public void addBucket(Locale language, int templateId) {

        Locale sourceLanguage = Locale.getDefault();

        Bucket bucket = new Bucket();
        bucket.setStreak(0);
        bucket.setDailyAverage(0);
        bucket.setLanguage(language);
        bucket.setUserLanguage(sourceLanguage);
        bucket.setTemplateId(templateId);

        Long bucketId = bucketRepository.insert(bucket);
        manageLetters.initializeLetters(bucketId);

        String token = "Token " + property.get("api-auth-token");
        String targetlang = language.getLanguage();
        String srcLang = sourceLanguage.getLanguage();

        syntactService.getPhrases(token, srcLang, targetlang, templateId)
                .enqueue(new Callback<List<PhraseResponse>>() {

                    @Override
                    public void onResponse(Call<List<PhraseResponse>> call,
                            Response<List<PhraseResponse>> response) {

                        if (response.isSuccessful()) {
                            List<PhraseResponse> body = response.body();
                            Log.i(TAG, "onResponse: " + body.size() + " phrases fetched");

                            List<SolvableTranslation> solvabeItems = phraseGenerator
                                    .createSolvabeItems(bucketId, body);

                            managePhrases.saveSolvableItems(solvabeItems);
                            Log.i(TAG,
                                    "onResponse: " + solvabeItems.size() + " new solvable items");
                        } else {
                            Log.e(TAG, "onResponse: Error receiving phrases");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PhraseResponse>> call, Throwable t) {

                        Log.e(TAG, "onFailure: Error receiving phrases", t);
                    }
                });
    }
}
