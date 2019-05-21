package com.alexkn.syntact.domain.usecase;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.alexkn.syntact.app.Property;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.model.SolvableItem;
import com.alexkn.syntact.domain.repository.BucketRepository;
import com.alexkn.syntact.domain.util.PhraseGenerator;
import com.alexkn.syntact.restservice.PhraseResponse;
import com.alexkn.syntact.restservice.SyntactService;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class ManageBuckets {

    private static final String TAG = ManageBuckets.class.getSimpleName();

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
    ManageBuckets() {

    }

    public void addBucket(Locale languageLeft, Locale languageRight) {

        Bucket activeBucket = new Bucket();
        activeBucket.setStreak(0);
        activeBucket.setDailyAverage(0);
        Long bucketId = bucketRepository.insert(activeBucket);
        manageLetters.initializeLetters(bucketId);

        String token = "Token " + property.get("api-auth-token");
        String lang;
        if (languageLeft.equals(Locale.ENGLISH)) {

            lang = languageRight.getLanguage();
        } else if (languageRight.equals(Locale.ENGLISH)) {
            lang = languageLeft.getLanguage();
        } else {
            lang = null;
        }
        syntactService.getPhrases(token, lang).enqueue(new Callback<List<PhraseResponse>>() {

            @Override
            public void onResponse(Call<List<PhraseResponse>> call,
                    Response<List<PhraseResponse>> response) {

                if (response.isSuccessful()) {
                    List<PhraseResponse> body = response.body();
                    Log.i(TAG, "onResponse: " + body.size() + " phrases fetched");

                    List<SolvableItem> solvabeItems = phraseGenerator
                            .createSolvabeItems(bucketId, body);

                    managePhrases.saveSolvableItems(solvabeItems);
                    Log.i(TAG, "onResponse: " + solvabeItems.size() + " new solvable items");
                } else {
                    Log.e(TAG, "onResponse: Error receiving phrases");
                }
            }

            @Override
            public void onFailure(Call<List<PhraseResponse>> call, Throwable t) {

                Log.e(TAG, "onFailure: Error receiving phrases", t);
            }
        });

        //        managePhrases.initializePhrases(insertedLanguageId, languageLeft, languageRight);
    }

    public List<Locale> getAvailableBuckets() {

        String[] languages = property.get("available-languages").split(",");
        return Arrays.stream(languages).map(Locale::new).collect(Collectors.toList());
    }

    public void removeLanguage(Long id) {

        bucketRepository.delete(id);
    }

    public LiveData<Bucket> getBucket(Long id) {

        return bucketRepository.findBucket(id);
    }

    public LiveData<List<Bucket>> getBuckets() {

        return bucketRepository.findAllBuckets();
    }
}
