package com.alexkn.syntact.domain.model;

import com.alexkn.syntact.domain.common.Identifiable;

import java.time.Instant;
import java.util.Locale;
import java.util.Objects;

public class Phrase implements Identifiable {

    private Long id;

    private String clue;

    private String solution;

    private String attempt;

    private Instant lastSolved;

    private Instant nextDueDate;

    private float easiness;

    private int consecutiveCorrectAnswers;

    private int timesSolved;

    private Locale clueLocale;

    private Locale solutionLocale;

    private Long languagePairId;

    public Phrase() {

    }

    public Phrase(Long id, String clue, String solution, String attempt, Instant lastSolved,
            Instant nextDueDate, float easiness, int consecutiveCorrectAnswers, int timesSolved,
            Locale clueLocale, Locale solutionLocale, Long languagePairId) {

        this.id = id;
        this.clue = clue;
        this.solution = solution;
        this.attempt = attempt;
        this.lastSolved = lastSolved;
        this.nextDueDate = nextDueDate;
        this.easiness = easiness;
        this.consecutiveCorrectAnswers = consecutiveCorrectAnswers;
        this.timesSolved = timesSolved;
        this.clueLocale = clueLocale;
        this.solutionLocale = solutionLocale;
        this.languagePairId = languagePairId;
    }

    public String getClue() {

        return clue;
    }

    public void setClue(String clue) {

        this.clue = clue;
    }

    public String getSolution() {

        return solution;
    }

    public void setSolution(String solution) {

        this.solution = solution;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getAttempt() {

        return attempt;
    }

    public void setAttempt(String attempt) {

        this.attempt = attempt;
    }

    public Instant getLastSolved() {

        return lastSolved;
    }

    public void setLastSolved(Instant lastSolved) {

        this.lastSolved = lastSolved;
    }

    public int getTimesSolved() {

        return timesSolved;
    }

    public void setTimesSolved(int timesSolved) {

        this.timesSolved = timesSolved;
    }

    public Locale getClueLocale() {

        return clueLocale;
    }



    public void setClueLocale(Locale clueLocale) {

        this.clueLocale = clueLocale;
    }

    public Locale getSolutionLocale() {

        return solutionLocale;
    }

    public void setSolutionLocale(Locale solutionLocale) {

        this.solutionLocale = solutionLocale;
    }

    public Instant getNextDueDate() {

        return nextDueDate;
    }

    public void setNextDueDate(Instant nextDueDate) {

        this.nextDueDate = nextDueDate;
    }

    public float getEasiness() {

        return easiness;
    }

    public void setEasiness(float easiness) {

        this.easiness = easiness;
    }

    public int getConsecutiveCorrectAnswers() {

        return consecutiveCorrectAnswers;
    }

    public void setConsecutiveCorrectAnswers(int consecutiveCorrectAnswers) {

        this.consecutiveCorrectAnswers = consecutiveCorrectAnswers;
    }

    public Long getLanguagePairId() {

        return languagePairId;
    }

    public void setLanguagePairId(Long languagePairId) {

        this.languagePairId = languagePairId;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phrase phrase = (Phrase) o;
        return id == phrase.id && Float.compare(phrase.easiness, easiness) == 0 &&
                consecutiveCorrectAnswers == phrase.consecutiveCorrectAnswers &&
                timesSolved == phrase.timesSolved && languagePairId == phrase.languagePairId &&
                Objects.equals(clue, phrase.clue) && Objects.equals(solution, phrase.solution) &&
                Objects.equals(attempt, phrase.attempt) &&
                Objects.equals(lastSolved, phrase.lastSolved) &&
                Objects.equals(nextDueDate, phrase.nextDueDate) &&
                Objects.equals(clueLocale, phrase.clueLocale) &&
                Objects.equals(solutionLocale, phrase.solutionLocale);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, clue, solution, attempt, lastSolved, nextDueDate, easiness,
                consecutiveCorrectAnswers, timesSolved, clueLocale, solutionLocale, languagePairId);
    }
}
