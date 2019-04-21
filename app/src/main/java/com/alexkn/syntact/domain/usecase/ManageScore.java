package com.alexkn.syntact.domain.usecase;

import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.domain.model.Phrase;
import com.alexkn.syntact.domain.repository.LanguagePairRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ManageScore {

    private static final int FIRST_TIME_SOLVE_SCORE = 100;

    private static final double LEVELING_COEFFICIENT = 0.16;

    @Inject
    LanguagePairRepository languagePairRepository;

    @Inject
    ManageScore() {

    }

    public void phraseSolved(Phrase phrase) {

        LanguagePair languagePair = languagePairRepository.find(phrase.getLanguagePairId());
        int timesSolved = phrase.getTimesSolved();
        int score = FIRST_TIME_SOLVE_SCORE / (timesSolved + 1);

        int newScore = languagePair.getScore() + score;

        languagePair.setScore(newScore);
        languagePair.setLevel(calculateLevel(newScore));
        languagePair.setStreak(languagePair.getStreak() + 1);

        languagePairRepository.update(languagePair);
    }

    public void resetStreak(Long languagePairId) {

        LanguagePair languagePair = languagePairRepository.find(languagePairId);
        languagePair.setStreak(0);
        languagePairRepository.update(languagePair);
    }

    private int calculateLevel(int score) {

        return (int) Math.floor(LEVELING_COEFFICIENT * Math.sqrt(score));
    }

    public int calculateMaxForLevel(int level) {

        return (int) Math.ceil(Math.pow(level / LEVELING_COEFFICIENT, 2));
    }
}
