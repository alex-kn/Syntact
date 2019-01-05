package com.alexkn.syntact.hangwords.logic;

import android.app.Application;

import com.alexkn.syntact.hangwords.dataaccess.impl.PhraseRepository;
import com.alexkn.syntact.hangwords.dataaccess.api.to.Phrase;
import com.alexkn.syntact.hangwords.logic.api.to.SolvablePhrase;
import com.alexkn.syntact.hangwords.ui.util.Letter;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

public class PhraseManagement {

    private final MutableLiveData<List<Phrase>> phrases = new MutableLiveData<>();

    private PhraseRepository phraseRepository;

    private List<SolvablePhrase> solvablePhrases = new ArrayList<>();

    private MediatorLiveData<List<SolvablePhrase>> solvablePhrasesLiveData = new MediatorLiveData<>();

    public PhraseManagement(Application application) {

        phraseRepository = new PhraseRepository(application);
        solvablePhrasesLiveData.addSource(phraseRepository.getAllPhrases(), this::handleNewPhrases);

    }

    public void solvePhrase(int id) {

        phraseRepository.updateLastSolved(id, Instant.now());
    }

    /**
     * Converts List of Phrases and populates the LiveData for SolvablePhrases
     *
     * @param phrases The List of Phrases
     */
    private void handleNewPhrases(List<Phrase> phrases) {

        phrases.sort(new PhraseAppearanceComparator());
        Map<Integer, SolvablePhrase> solvablePhraseMap = solvablePhrases.stream()
                .collect(Collectors.toMap(SolvablePhrase::getId, Function.identity()));

        solvablePhrases = phrases.stream().map(phrase -> solvablePhraseMap
                .getOrDefault(phrase.getId(), convertPhraseToUiModel(phrase)))
                .collect(Collectors.toList());

        solvablePhrasesLiveData.setValue(solvablePhrases);
    }

    public boolean solvePhrase(SolvablePhrase solvablePhrase, Letter letter) {

        if (!isLetterCorrect(solvablePhrase, letter)) {
            return false;
        }
        SolvablePhrase newSolvablePhrase = updateCurrentText(solvablePhrase, letter);
        if (newSolvablePhrase.isSolved()) {
            solvePhrase(solvablePhrase.getId());
        }
        ArrayList<SolvablePhrase> tmpPhrases = new ArrayList<>(solvablePhrases);
        tmpPhrases.set(solvablePhrases.indexOf(solvablePhrase), newSolvablePhrase);
        solvablePhrasesLiveData.setValue(tmpPhrases);
        solvablePhrases = tmpPhrases;
        return true;
    }

    private SolvablePhrase updateCurrentText(SolvablePhrase solvablePhrase, Letter letter) {

        String solution = solvablePhrase.getSolution();
        IntStream indices = IntStream.range(0, solution.length()).filter(i -> StringUtils
                .equalsIgnoreCase(letter.toString(), String.valueOf(solution.charAt(i))));
        StringBuilder newCurrentText = new StringBuilder(solvablePhrase.getCurrentText());
        indices.forEach(i -> newCurrentText.setCharAt(i, letter.getCharacter()));

        return new SolvablePhrase(solvablePhrase.getId(), solvablePhrase.getClue(),
                solvablePhrase.getSolution(), newCurrentText.toString());
    }

    private boolean isLetterCorrect(SolvablePhrase solvablePhrase, Letter letter) {

        return StringUtils.containsIgnoreCase(solvablePhrase.getSolution(), letter.toString()) &&
                !StringUtils.containsIgnoreCase(solvablePhrase.getCurrentText(), letter.toString());
    }

    public LiveData<List<SolvablePhrase>> getSolvablePhrasesLiveData() {

        return solvablePhrasesLiveData;
    }

    private SolvablePhrase convertPhraseToUiModel(Phrase phrase) {

        return new SolvablePhrase(phrase.getId(), phrase.getClue(), phrase.getSolution(),
                StringUtils.repeat(Letter.EMPTY, phrase.getSolution().length()));
    }

    private static class PhraseAppearanceComparator implements Comparator<Phrase> {

        @Override
        public int compare(Phrase o1, Phrase o2) {

            int result;
            result = ObjectUtils.compare(o1.getLastSolved(), o2.getLastSolved());
            if (result == 0) {
                result = ObjectUtils.compare(o1.getClue(), o2.getClue());
            }
            return result;
        }
    }
}
