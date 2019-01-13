package com.alexkn.syntact.domain.usecase;

import com.alexkn.syntact.dataaccess.phrase.PhraseEntity;
import com.alexkn.syntact.domain.model.Phrase;
import com.alexkn.syntact.domain.repository.PhraseRepository;
import com.alexkn.syntact.presentation.hangman.board.Letter;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

@Singleton
public class PhraseUseCase {

    @Inject
    PhraseRepository phraseRepository;

    private List<Phrase> solvablePhrases = new ArrayList<>();

    private LiveData<List<Phrase>> phrases;

    @Inject
    public PhraseUseCase() { }

    public boolean solvePhrase(Phrase phrase, Letter letter) {

        if (!isLetterCorrect(phrase, letter)) {
            return false;
        }
        String attempt = updateCurrentText(phrase, letter);
        if (!attempt.contains(Letter.EMPTY.toString())) {
            phraseRepository.updateLastSolved(phrase.getId(), Instant.now());
        }
        phraseRepository.updateAttempt(phrase.getId(), attempt);

//        ArrayList<Phrase > tmpPhrases = new ArrayList<>(solvablePhrases);
//        tmpPhrases.set(solvablePhrases.indexOf(phrase), newSolvablePhrase);
//                phrases.setValue(tmpPhrases);
//        solvablePhrases = tmpPhrases;
        return true;
    }

    private String updateCurrentText(Phrase phrase, Letter letter) {

        String solution = phrase.getSolution();
        IntStream indices = IntStream.range(0, solution.length()).filter(i -> StringUtils
                .equalsIgnoreCase(letter.toString(), String.valueOf(solution.charAt(i))));
        StringBuilder newCurrentText = new StringBuilder(phrase.getAttempt());
        indices.forEach(i -> newCurrentText.setCharAt(i, letter.getCharacter()));
        return newCurrentText.toString();

//        return new Phrase(phrase.getId(), phrase.getClue(), phrase.getSolution(),
//                newCurrentText.toString(), phrase.getLastSolved(), phrase.getTimesSolved());
    }

    private boolean isLetterCorrect(Phrase solvablePhrase, Letter letter) {

        return StringUtils.containsIgnoreCase(solvablePhrase.getSolution(), letter.toString()) &&
                !StringUtils.containsIgnoreCase(solvablePhrase.getAttempt(), letter.toString());
    }

    public LiveData<List<Phrase>> getPhrases() {

        if (phrases == null) {
            //            phraseRepository.insert(new PhraseEntity("A", "A", "_"));
            //            phraseRepository.insert(new PhraseEntity("B", "B", "_"));
            //            phraseRepository.insert(new PhraseEntity("C", "C", "_"));
            //            phraseRepository.insert(new PhraseEntity("D", "D", "_"));
            //            phraseRepository.insert(new PhraseEntity("E", "E", "_"));
            //            phraseRepository.insert(new PhraseEntity("F", "F", "_"));
            //            phraseRepository.insert(new PhraseEntity("G", "G", "_"));
            //            phraseRepository.insert(new PhraseEntity("H", "H", "_"));
            //            phraseRepository.insert(new PhraseEntity("I", "I", "_"));
            //            phraseRepository.insert(new PhraseEntity("J", "J", "_"));
            phrases = phraseRepository.getAllPhrases();
        }
        return phrases;
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
