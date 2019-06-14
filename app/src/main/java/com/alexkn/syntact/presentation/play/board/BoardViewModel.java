package com.alexkn.syntact.presentation.play.board;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alexkn.syntact.domain.common.LetterColumn;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.model.Letter;
import com.alexkn.syntact.domain.model.cto.SolvableTranslationCto;
import com.alexkn.syntact.domain.usecase.bucket.ManageBuckets;
import com.alexkn.syntact.domain.usecase.play.ManageLetters;
import com.alexkn.syntact.domain.usecase.play.ManageSolvableItems;

import java.time.Instant;
import java.util.List;

import javax.inject.Inject;

public class BoardViewModel extends ViewModel {

    private final Instant startTime;

    ManageSolvableItems manageSolvableItems;

    ManageBuckets manageBuckets;

    ManageLetters manageLetters;

    private Long bucketId;

    private LiveData<Bucket> bucket;

    private LiveData<List<SolvableTranslationCto>> phrases;

    private LiveData<List<Letter>> lettersLeft;

    private LiveData<List<Letter>> lettersRight;

    @Inject
    public BoardViewModel(ManageSolvableItems manageSolvableItems, ManageBuckets manageBuckets, ManageLetters manageLetters) {

        super();
        this.manageSolvableItems = manageSolvableItems;
        this.manageBuckets = manageBuckets;
        this.manageLetters = manageLetters;
        this.startTime = Instant.now();
    }

    public void setBucketId(Long bucketId) {

        this.bucketId = bucketId;
    }

    boolean solve(SolvableTranslationCto solvableTranslation, Letter letter) {

        boolean successful = manageSolvableItems.isLetterCorrect(solvableTranslation, letter.getCharacter());
        AsyncTask.execute(() -> manageSolvableItems.makeAttempt(solvableTranslation, letter.getCharacter()));
        if (successful) {
            AsyncTask.execute(() -> manageLetters.replaceLetter(letter));
        }
        return successful;
    }

    public void reloadLetters() {

        manageLetters.reloadLetters(bucketId);
    }

    public void initLanguage(Long bucketId) {

        this.bucketId = bucketId;
        bucket = manageBuckets.getBucket(bucketId);
        phrases = manageSolvableItems.getSolvableTranslations(bucketId);
        lettersLeft = manageLetters.getLetters(bucketId, LetterColumn.LEFT);
        lettersRight = manageLetters.getLetters(bucketId, LetterColumn.RIGHT);
    }

    public void triggerPhrasesFetch() {

        AsyncTask.execute(() -> manageSolvableItems.fetchSolvableItems(bucketId, startTime));

    }

    LiveData<List<SolvableTranslationCto>> getSolvablePhrases() {

        return phrases;
    }

    LiveData<List<Letter>> getLettersLeft() {

        return lettersLeft;
    }

    LiveData<List<Letter>> getLettersRight() {

        return lettersRight;
    }

    LiveData<Bucket> getBucket() {

        return bucket;
    }

}
