package com.alexkn.syntact.domain.usecase.play;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;

import com.alexkn.syntact.R;
import com.alexkn.syntact.app.Property;
import com.alexkn.syntact.domain.model.Attempt;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.model.Clue;
import com.alexkn.syntact.domain.model.SolvableItem;
import com.alexkn.syntact.domain.model.cto.SolvableTranslationCto;
import com.alexkn.syntact.domain.repository.AttemptRepository;
import com.alexkn.syntact.domain.repository.BucketRepository;
import com.alexkn.syntact.domain.repository.ClueRepository;
import com.alexkn.syntact.domain.repository.SolvableItemRepository;
import com.alexkn.syntact.restservice.Phrase;
import com.alexkn.syntact.restservice.SyntactService;
import com.alexkn.syntact.restservice.Translation;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.IntStream;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class ManageSolvableItems {

    private static final String TAG = ManageSolvableItems.class.getSimpleName();

    @Inject
    SolvableItemRepository solvableItemRepository;

    @Inject
    AttemptRepository attemptRepository;

    @Inject
    ClueRepository clueRepository;

    @Inject
    Application application;

    @Inject
    BucketRepository bucketRepository;

    @Inject
    ManageScore manageScore;

    @Inject
    SyntactService syntactService;

    @Inject
    Property property;

    private MediatorLiveData<List<SolvableTranslationCto>> solvableTranslations;

    @Inject
    ManageSolvableItems() {

    }

    public LiveData<List<SolvableTranslationCto>> getSolvableTranslations(Long bucketId) {

        return solvableItemRepository.getSolvableTranslationsDueBefore(bucketId, Instant.now());
    }

    public void fetchSolvableItems(long bucketId) {

        Data data = new Data.Builder().putLong("bucketId", bucketId).putLong("timestamp",Instant.now().toEpochMilli()).build();

        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(FetchPhrasesWorker.class).setInputData(data).build();
        WorkManager.getInstance().enqueueUniqueWork(FetchPhrasesWorker.class.getName(), ExistingWorkPolicy.KEEP, workRequest);

    }

    public void makeAttempt(SolvableTranslationCto solvableTranslation, Character character) {

        String attempt = updateCurrentAttempt(solvableTranslation, character);
        if (!attempt.contains(application.getString(R.string.empty))) {
            solvePhrase(solvableTranslation);
        } else {
            attemptRepository.updateAttempt(solvableTranslation.getId(), attempt);//TODO
        }
    }

    private void solvePhrase(SolvableTranslationCto solvableTranslation) {

        SolvableItem solvableItem = solvableTranslation.getSolvableItem();

        int performanceRating = 3;
        float easiness = solvableItem.getEasiness();
        easiness += -0.80 + 0.28 * performanceRating + 0.02 * performanceRating * performanceRating;

        int consecutiveCorrectAnswers = solvableItem.getConsecutiveCorrectAnswers() + 1;

        double daysToAdd = 6 * Math.pow(easiness, consecutiveCorrectAnswers - 1d);
        Instant nextDueDate = Instant.now().plus(Math.round(daysToAdd), ChronoUnit.DAYS);

        solvableItem.setEasiness(easiness);
        solvableItem.setConsecutiveCorrectAnswers(consecutiveCorrectAnswers);
        solvableItem.setNextDueDate(nextDueDate);
        solvableItem.setLastSolved(Instant.now());
        attemptRepository
                .updateAttempt(solvableItem.getId(), StringUtils.repeat(application.getString(R.string.empty), solvableItem.getText().length()));

        solvableItemRepository.update(solvableItem);
    }

    private String updateCurrentAttempt(SolvableTranslationCto solvableTranslation, Character character) {

        SolvableItem solvableItem = solvableTranslation.getSolvableItem();

        String solution = solvableItem.getText();

        IntStream indices = IntStream.range(0, solution.length())
                .filter(i -> StringUtils.equalsIgnoreCase(character.toString(), String.valueOf(solution.charAt(i))));
        StringBuilder newCurrentText = new StringBuilder(solvableTranslation.getAttempt().getText());
        indices.forEach(i -> newCurrentText.setCharAt(i, character));
        return newCurrentText.toString();
    }

    public boolean isLetterCorrect(SolvableTranslationCto solvableTranslation, Character character) {

        return StringUtils.containsIgnoreCase(solvableTranslation.getSolvableItem().getText(), character.toString()) &&
                !StringUtils.containsIgnoreCase(solvableTranslation.getAttempt().getText(), character.toString());
    }

    //    public void saveSolvableItems(List<SolvableItem> solvableItems) {
    //
    //        List<Character> specialCharacters = Arrays.asList('?', '\'', ',', '.', '-', ' ', ';');
    //
    //        solvableItems.forEach(solvableItem -> specialCharacters.forEach(character -> solvableItem
    //                .setAttempt(updateCurrentAttempt(solvableItem, character))));
    //        Collections.shuffle(solvableItems);
    //        solvableItemRepository.insert(solvableItems);
    //    }
    //
    //    public void initializePhrases(Long insertedLanguageId, Locale languageLeft,
    //            Locale languageRight) {
    //
    //        List<Character> specialCharacters = Arrays.asList('?', '\'', ',', '.', '-', ' ', ';');
    //
    //        if (languageLeft.equals(Locale.GERMAN) && languageRight.equals(Locale.ENGLISH)) {
    //            List<SolvableItem> solvableItems = phraseGenerator.generateGermanEnglishPhrases();
    //            solvableItems.forEach(phrase -> phrase.setBucketId(insertedLanguageId));
    //            saveSolvableItems(solvableItems);
    //        }
    //    }
}
