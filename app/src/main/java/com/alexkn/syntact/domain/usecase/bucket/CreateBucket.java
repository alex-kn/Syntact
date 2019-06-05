package com.alexkn.syntact.domain.usecase.bucket;

import android.app.Application;
import android.util.Log;

import com.alexkn.syntact.R;
import com.alexkn.syntact.app.Property;
import com.alexkn.syntact.domain.model.Attempt;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.model.Clue;
import com.alexkn.syntact.domain.model.SolvableItem;
import com.alexkn.syntact.domain.repository.AttemptRepository;
import com.alexkn.syntact.domain.repository.BucketRepository;
import com.alexkn.syntact.domain.repository.ClueRepository;
import com.alexkn.syntact.domain.repository.SolvableItemRepository;
import com.alexkn.syntact.domain.usecase.play.ManageLetters;
import com.alexkn.syntact.domain.usecase.play.ManagePhrases;
import com.alexkn.syntact.restservice.Phrase;
import com.alexkn.syntact.restservice.SyntactService;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

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
    ManagePhrases managePhrases;

    @Inject
    Property property;

    @Inject
    SyntactService syntactService;

    @Inject
    Application application;

    @Inject
    CreateBucket() {}

    public void addBucket(Locale language, int templateId) {

        Locale sourceLanguage = Locale.getDefault();

        Bucket bucket = new Bucket();
        bucket.setCreatedAt(Instant.now());
        bucket.setLanguage(language);
        bucket.setUserLanguage(sourceLanguage);

        String token = "Token " + property.get("api-auth-token");

        Long bucketId = bucketRepository
                .insert(bucket);//TODO post bucket to API, get id from backend
        manageLetters.initializeLetters(bucketId);

        //TODO fetch phrases
    }

    private void createSolvabeItems(Long bucketId, List<Phrase> phraseRespons) {

        phraseRespons.forEach(phrase -> createSolvableItem(bucketId, phrase));
    }

    private void createSolvableItem(Long bucketId, Phrase phrase) {

        SolvableItem solvableItem = new SolvableItem();
        Attempt attempt = new Attempt();
        Clue clue = new Clue();

        solvableItem.setId(phrase.getId());
        attempt.setSolvableItemId(phrase.getId());
        clue.setSolvableItemId(phrase.getId());
        solvableItem.setBucketId(bucketId);

        solvableItem.setConsecutiveCorrectAnswers(0);
        solvableItem.setEasiness(2.5f);
        solvableItem.setNextDueDate(Instant.now());
        solvableItem.setTimesSolved(0);

        solvableItem.setText(phrase.getText());

        attempt.setText(StringUtils
                .repeat(application.getString(R.string.empty), phrase.getText().length()));

        clue.setText(phrase.getTranslations().get(0).getText());

        solvableItemRepository.insert(solvableItem);
        clueRepository.insert(clue);
        attemptRepository.insert(attempt);
        Log.i(TAG, "createSolvableItem: Item created - " + phrase.getText());
    }
}
