package com.alexkn.syntact.presentation.hangman;

import android.app.Application;
import android.os.AsyncTask;

import com.alexkn.syntact.app.ApplicationComponentProvider;
import com.alexkn.syntact.domain.common.LetterColumn;
import com.alexkn.syntact.domain.model.Bucket;
import com.alexkn.syntact.domain.model.Letter;
import com.alexkn.syntact.domain.model.Phrase;
import com.alexkn.syntact.domain.usecase.ManageBuckets;
import com.alexkn.syntact.domain.usecase.ManageLetters;
import com.alexkn.syntact.domain.usecase.ManagePhrases;
import com.alexkn.syntact.domain.usecase.ManageScore;
import com.alexkn.syntact.presentation.common.DaggerViewComponent;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class HangmanViewModel extends AndroidViewModel {

    @Inject
    ManagePhrases managePhrases;

    @Inject
    ManageBuckets manageBuckets;

    @Inject
    ManageLetters manageLetters;

    @Inject
    ManageScore manageScore;

    private MediatorLiveData<Integer> maxScore = new MediatorLiveData<>();

    private Long bucketId;

    private LiveData<Bucket> bucket;

    private LiveData<List<Phrase>> phrases;

    private LiveData<List<Letter>> lettersLeft;

    private LiveData<List<Letter>> lettersRight;

    public HangmanViewModel(Application application) {

        super(application);

        DaggerViewComponent.builder().applicationComponent(
                ((ApplicationComponentProvider) getApplication()).getApplicationComponent()).build()
                .inject(this);
    }

    public void setBucketId(Long bucketId) {

        this.bucketId = bucketId;
    }

    boolean solve(Phrase solvablePhrase, Letter letter) {

        boolean successful = managePhrases.isLetterCorrect(solvablePhrase, letter.getCharacter());
        AsyncTask.execute(() -> managePhrases.makeAttempt(solvablePhrase, letter.getCharacter()));
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
        phrases = managePhrases.getPhrases(bucketId);
        lettersLeft = manageLetters.getLetters(bucketId, LetterColumn.LEFT);
        lettersRight = manageLetters.getLetters(bucketId, LetterColumn.RIGHT);
    }

    public int calculateMaxScoreForLevel(int level) {

        return manageScore.calculateMaxForLevel(level);
    }

    LiveData<List<Phrase>> getSolvablePhrases() {

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
