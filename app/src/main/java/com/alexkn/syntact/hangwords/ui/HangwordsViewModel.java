package com.alexkn.syntact.hangwords.ui;

import com.alexkn.syntact.hangwords.dataaccess.Phrase;
import com.alexkn.syntact.hangwords.logic.LetterManagement;
import com.alexkn.syntact.hangwords.logic.PhraseManagement;
import com.alexkn.syntact.hangwords.logic.SolvablePhrase;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class HangwordsViewModel extends ViewModel {


    private final MediatorLiveData<List<Letter>> letters1 = new MediatorLiveData<>();
    private final MediatorLiveData<List<Letter>> letters2 = new MediatorLiveData<>();
    private final MediatorLiveData<List<Letter>> letters3 = new MediatorLiveData<>();

    private final LiveData<List<SolvablePhrase>> solvablePhrases;

    public HangwordsViewModel() {

        solvablePhrases = Transformations.map(PhraseManagement.getInstance().getPhrases(),
                HangwordsViewModel::convertPhrasesToUiModel);

        letters1.addSource(LetterManagement.getInstance().getLetters1(), letters1::setValue);
        letters2.addSource(LetterManagement.getInstance().getLetters2(), letters2::setValue);
        letters3.addSource(LetterManagement.getInstance().getLetters3(), letters3::setValue);

    }
    private static List<SolvablePhrase> convertPhrasesToUiModel(List<Phrase> phrases) {
        phrases.sort(new PhraseAppearanceComparator());
        return phrases.stream().map(phrase -> new SolvablePhrase(phrase.getId(), phrase.getClue(),
                phrase.getSolution(), StringUtils.repeat('_', phrase.getSolution().length())))
                .collect(Collectors.toList());
    }

    public LiveData<List<Letter>> getLetters1() {
        return letters1;
    }

    public LiveData<List<Letter>> getLetters2() {
        return letters2;
    }

    public LiveData<List<Letter>> getLetters3() {
        return letters3;
    }

    public LiveData<List<SolvablePhrase>> getSolvablePhrases() {
        return solvablePhrases;
    }
    private static class PhraseAppearanceComparator implements Comparator<Phrase> {

        @Override
        public int compare(Phrase o1, Phrase o2) {
            return ObjectUtils.compare(o1.getLastSolved(), o2.getLastSolved());
        }
    }
}
