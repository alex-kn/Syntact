package com.alexkn.syntact.domain.usecase.play;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.app.Property;
import com.alexkn.syntact.domain.model.Attempt;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.model.Clue;
import com.alexkn.syntact.domain.model.SolvableItem;
import com.alexkn.syntact.domain.repository.AttemptRepository;
import com.alexkn.syntact.domain.repository.BucketRepository;
import com.alexkn.syntact.domain.repository.ClueRepository;
import com.alexkn.syntact.domain.repository.SolvableItemRepository;
import com.alexkn.syntact.restservice.Phrase;
import com.alexkn.syntact.restservice.SyntactService;
import com.alexkn.syntact.restservice.Translation;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;

public class FetchPhrasesWorker extends Worker {

    private static final String TAG = FetchPhrasesWorker.class.getSimpleName();

    @Inject
    BucketRepository bucketRepository;

    @Inject
    SolvableItemRepository solvableItemRepository;

    @Inject
    AttemptRepository attemptRepository;

    @Inject
    ClueRepository clueRepository;

    @Inject
    SyntactService syntactService;

    @Inject
    Property property;

    public FetchPhrasesWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {

        super(context, workerParams);
        ((ApplicationComponentProvider) context).getApplicationComponent().inject(this);
    }

    @NonNull
    @Override
    public Result doWork() {

        //TODO do only if items on phone <= itemcount in bucket
        //TODO regularly update bucket phrase count
        Data inputData = getInputData();
        long bucketId = inputData.getLong("bucketId", -1L);
        long timestamp = inputData.getLong("timestamp", Instant.now().toEpochMilli());
        Instant now = Instant.ofEpochMilli(timestamp).truncatedTo(ChronoUnit.MINUTES);
        if (bucketId == -1L) {
            return Result.failure();
        }

        Bucket bucket = bucketRepository.find(bucketId);

        String token = "Token " + property.get("api-auth-token");

        Long minFetchId = solvableItemRepository.getMaxId(bucket.getId());

        Log.i(TAG, "Fetching 5 phrases with ids above " + minFetchId);

        try {
            Response<List<Phrase>> phraseResponse = syntactService.getPhrases(token, bucket.getPhrasesUrl(), minFetchId, 5).execute();

            if (phraseResponse.isSuccessful()) {
                List<Phrase> phrases = phraseResponse.body();
                if (phrases != null) {
                    Log.i(TAG, phrases.size() + " phrases fetched");
                    for (Phrase phrase : phrases) {
                        SolvableItem solvableItem = new SolvableItem();
                        solvableItem.setText(phrase.getText());
                        solvableItem.setBucketId(bucket.getId());
                        solvableItem.setId(phrase.getId());
                        solvableItem.setConsecutiveCorrectAnswers(0);
                        solvableItem.setEasiness(2.5f);
                        solvableItem.setNextDueDate(now);
                        solvableItem.setTimesSolved(0);

                        Attempt attempt = new Attempt();
                        attempt.setSolvableItemId(phrase.getId());
                        attempt.setText(phrase.getText().replaceAll("[a-zA-Z]", "_"));

                        Log.i(TAG, "Fetched Phrase " + phrase.getText() + ". Fetching translation...");

                        Response<List<Translation>> translationResponse = syntactService
                                .getTranslations(token, phrase.getTranslationsUrl(), bucket.getUserLanguage().getLanguage()).execute();

                        if (translationResponse.isSuccessful() && translationResponse.body() != null) {
                            if (translationResponse.body().size() == 0) {
                                Log.i(TAG, "No Translation to " + bucket.getUserLanguage().getDisplayLanguage() + " for Phrase " + phrase.getText() +
                                        " found");
                            } else {
                                if (translationResponse.body().size() > 1) {
                                    Log.i(TAG, "Multiple Translations not yet supported, using first translation and discard others");
                                }
                                Translation translation = translationResponse.body().get(0);

                                Clue clue = new Clue();
                                clue.setSolvableItemId(phrase.getId());
                                clue.setText(translation.getText());
                                clue.setId(translation.getId());
                                solvableItemRepository.insert(solvableItem);
                                attemptRepository.insert(attempt);
                                clueRepository.insert(clue);
                                Log.i(TAG, "Inserted Clue for Phrase " + phrase.getText());
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success();
    }
}
