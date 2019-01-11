package com.alexkn.syntact.domain.usecase;

import android.app.Application;

import com.alexkn.syntact.dataaccess.phrase.PhraseRepositoryImpl;
import com.alexkn.syntact.dataaccess.phrase.PhraseEntity;
import com.alexkn.syntact.domain.model.Phrase;
import com.alexkn.syntact.presentation.hangman.board.Letter;

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

public class PhraseUseCase {

    private final MutableLiveData<List<PhraseEntity>> phrases = new MutableLiveData<>();

    private PhraseRepositoryImpl phraseRepository;

    private List<Phrase> solvablePhrases = new ArrayList<>();

    private MediatorLiveData<List<Phrase>> solvablePhrasesLiveData = new MediatorLiveData<>();

    public PhraseUseCase(Application application) {

        phraseRepository = new PhraseRepositoryImpl(application);
        solvablePhrasesLiveData.addSource(phraseRepository.getAllPhrases(), this::handleNewPhrases);
//        phraseRepository.deleteAll();
//        phraseRepository.insert(new PhraseEntity("A", "A"));
//        phraseRepository.insert(new PhraseEntity("B", "B"));
//        phraseRepository.insert(new PhraseEntity("C", "C"));
//        phraseRepository.insert(new PhraseEntity("D", "D"));
//        phraseRepository.insert(new PhraseEntity("E", "E"));
//        phraseRepository.insert(new PhraseEntity("F", "F"));
//        phraseRepository.insert(new PhraseEntity("G", "G"));
//        phraseRepository.insert(new PhraseEntity("H", "H"));
//        phraseRepository.insert(new PhraseEntity("I", "I"));
//        phraseRepository.insert(new PhraseEntity("J", "J"));
    }

    public void solvePhrase(int id) {

        phraseRepository.updateLastSolved(id, Instant.now());
    }

    /**
     * Converts List of Phrases and populates the LiveData for SolvablePhrases
     *
     * @param phraseEntities The List of Phrases
     */
    private void handleNewPhrases(List<PhraseEntity> phraseEntities) {

        phraseEntities.sort(new PhraseAppearanceComparator());
        Map<Integer, Phrase> solvablePhraseMap = solvablePhrases.stream()
                .collect(Collectors.toMap(Phrase::getId, Function.identity()));

        solvablePhrases = phraseEntities.stream().map(phrase -> solvablePhraseMap
                .getOrDefault(phrase.getId(), convertPhraseToUiModel(phrase)))
                .collect(Collectors.toList());

        solvablePhrasesLiveData.setValue(solvablePhrases);
    }

    public boolean solvePhrase(Phrase solvablePhrase, Letter letter) {

        if (!isLetterCorrect(solvablePhrase, letter)) {
            return false;
        }
        Phrase newSolvablePhrase = updateCurrentText(solvablePhrase, letter);
        if (newSolvablePhrase.isSolved()) {
            solvePhrase(solvablePhrase.getId());
        }
        ArrayList<Phrase> tmpPhrases = new ArrayList<>(solvablePhrases);
        tmpPhrases.set(solvablePhrases.indexOf(solvablePhrase), newSolvablePhrase);
        solvablePhrasesLiveData.setValue(tmpPhrases);
        solvablePhrases = tmpPhrases;
        return true;
    }

    private Phrase updateCurrentText(Phrase solvablePhrase, Letter letter) {

        String solution = solvablePhrase.getSolution();
        IntStream indices = IntStream.range(0, solution.length()).filter(i -> StringUtils
                .equalsIgnoreCase(letter.toString(), String.valueOf(solution.charAt(i))));
        StringBuilder newCurrentText = new StringBuilder(solvablePhrase.getCurrentText());
        indices.forEach(i -> newCurrentText.setCharAt(i, letter.getCharacter()));

        return new Phrase(solvablePhrase.getId(), solvablePhrase.getClue(),
                solvablePhrase.getSolution(), newCurrentText.toString());
    }

    private boolean isLetterCorrect(Phrase solvablePhrase, Letter letter) {

        return StringUtils.containsIgnoreCase(solvablePhrase.getSolution(), letter.toString()) &&
                !StringUtils.containsIgnoreCase(solvablePhrase.getCurrentText(), letter.toString());
    }

    public LiveData<List<Phrase>> getSolvablePhrasesLiveData() {

        return solvablePhrasesLiveData;
    }

    private Phrase convertPhraseToUiModel(PhraseEntity phraseEntity) {

        return new Phrase(phraseEntity.getId(), phraseEntity.getClue(), phraseEntity.getSolution(),
                StringUtils.repeat(Letter.EMPTY, phraseEntity.getSolution().length()));
    }

    private static class PhraseAppearanceComparator implements Comparator<PhraseEntity> {

        @Override
        public int compare(PhraseEntity o1, PhraseEntity o2) {

            int result;
            result = ObjectUtils.compare(o1.getLastSolved(), o2.getLastSolved());
            if (result == 0) {
                result = ObjectUtils.compare(o1.getClue(), o2.getClue());
            }
            return result;
        }
    }
}
