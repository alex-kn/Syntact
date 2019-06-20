package com.alexkn.syntact.restservice;

import android.util.Log;

import com.alexkn.syntact.app.Property;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.model.Clue;
import com.alexkn.syntact.domain.model.SolvableItem;
import com.alexkn.syntact.domain.model.cto.SolvableTranslationCto;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;

@Singleton
public class SolvableItemRemoteRepository {

    private static final String TAG = SolvableItemRemoteRepository.class.getSimpleName();

    private Property property;

    private SyntactService syntactService;

    @Inject
    public SolvableItemRemoteRepository(Property property, SyntactService syntactService) {

        this.property = property;
        this.syntactService = syntactService;
    }

    public List<SolvableTranslationCto> fetchNewTranslations(Bucket bucket, Instant now, long minFetchId, int count) {

        String token = "Token " + property.get("api-auth-token");

        Log.i(TAG, "Fetching " + count + " phrases with ids above " + minFetchId);

        List<SolvableTranslationCto> solvableTranslationCtos = new ArrayList<>();

        try {
            Response<List<Phrase>> phraseResponse = syntactService.getPhrases(token, bucket.getPhrasesUrl(), minFetchId, count).execute();

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
                                SolvableTranslationCto solvableTranslationCto = new SolvableTranslationCto();
                                solvableTranslationCto.setSolvableItem(solvableItem);
                                solvableTranslationCto.setClue(clue);
                                solvableTranslationCtos.add(solvableTranslationCto);
                                Log.i(TAG, "Inserted Translation for Phrase " + phrase.getText());
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return solvableTranslationCtos;
    }
}
