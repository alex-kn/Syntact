package com.alexkn.syntact.domain.usecase.play;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.alexkn.syntact.R;
import com.alexkn.syntact.app.Property;
import com.alexkn.syntact.domain.model.SolvableItem;
import com.alexkn.syntact.domain.model.cto.SolvableTranslationCto;
import com.alexkn.syntact.domain.repository.AttemptRepository;
import com.alexkn.syntact.domain.repository.BucketRepository;
import com.alexkn.syntact.domain.repository.ClueRepository;
import com.alexkn.syntact.domain.repository.SolvableItemRepository;
import com.alexkn.syntact.restservice.SolvableItemRemoteRepository;
import com.alexkn.syntact.restservice.SyntactService;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.IntStream;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Maybe;

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
    Context context;

    @Inject
    BucketRepository bucketRepository;

    @Inject
    SyntactService syntactService;

    @Inject
    Property property;

    private MediatorLiveData<List<SolvableTranslationCto>> solvableTranslations;

    private SolvableItemRemoteRepository solvableItemRemoteRepository;

    @Inject
    ManageSolvableItems(SolvableItemRemoteRepository solvableItemRemoteRepository) {

        this.solvableItemRemoteRepository = solvableItemRemoteRepository;
    }

    public LiveData<List<SolvableTranslationCto>> getSolvableTranslations(Long bucketId) {

        return solvableItemRepository.getSolvableTranslationsDueBefore(bucketId, Instant.now());
    }

    public Maybe<SolvableTranslationCto[]> getNextSolvableTranslations(Long bucketId, Instant time, int count) {

        Maybe<SolvableTranslationCto[]> translations = solvableItemRepository.getNextTranslationDueBefore(bucketId, time, count);

        return translations.flatMap(items -> {
            if (items.length == count) {
                Log.i(TAG, "All translations present");
                return Maybe.just(items);
            } else {
                Log.i(TAG, "Translations missing, fetching remote");
                List<SolvableTranslationCto> solvableTranslationCtos = solvableItemRemoteRepository.fetchNewTranslations(bucketId, time, count);
                return Maybe.just(solvableTranslationCtos.toArray(new SolvableTranslationCto[0]));
            }
        });
    }

    public void fetchSolvableItems(long bucketId, Instant startTime) {

        Data data = new Data.Builder().putLong("bucketId", bucketId).putLong("timestamp", startTime.toEpochMilli()).build();

        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(FetchPhrasesWorker.class).setInputData(data).build();
        WorkManager.getInstance().enqueueUniqueWork(FetchPhrasesWorker.class.getName(), ExistingWorkPolicy.KEEP, workRequest);
    }

    public void makeAttempt(SolvableTranslationCto solvableTranslation, Character character) {

        String attempt = updateCurrentAttempt(solvableTranslation, character);
        if (!attempt.contains(context.getString(R.string.empty))) {
            solvePhrase(solvableTranslation);
        } else {
            attemptRepository.updateAttempt(solvableTranslation.getId(), attempt);//TODO
        }
    }

    public void solvePhrase(SolvableTranslationCto solvableTranslation) {

        SolvableItem solvableItem = solvableTranslation.getSolvableItem();
        Log.i(TAG, "Solving Phrase " + solvableItem.getText());

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
        solvableItem.setTimesSolved(solvableItem.getTimesSolved() + 1);
        attemptRepository.updateAttempt(solvableItem.getId(), StringUtils.repeat(context.getString(R.string.empty), solvableItem.getText().length()));

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
}
