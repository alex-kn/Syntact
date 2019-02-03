package com.alexkn.syntact.domain.model;

import com.alexkn.syntact.domain.common.Identifiable;

import java.time.Instant;
import java.util.Locale;
import java.util.Objects;

public class Phrase implements Identifiable {

    private int id;

    private String clue;

    private String solution;

    private String attempt;

    private Instant lastSolved;

    private int timesSolved;

    private Locale clueLocale;

    private Locale solutionLocale;

    public Phrase(){

    }

    public Phrase(int id, String clue, String solution, String attempt, Instant lastSolved,
            int timesSolved, Locale clueLocale, Locale solutionLocale) {

        this.id = id;
        this.clue = clue;
        this.solution = solution;
        this.attempt = attempt;
        this.lastSolved = lastSolved;
        this.timesSolved = timesSolved;
        this.clueLocale = clueLocale;
        this.solutionLocale = solutionLocale;
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

    public Integer getId() {

        return id;
    }

    public void setId(int id) {

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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phrase phrase = (Phrase) o;
        return id == phrase.id && timesSolved == phrase.timesSolved &&
                Objects.equals(clue, phrase.clue) && Objects.equals(solution, phrase.solution) &&
                Objects.equals(attempt, phrase.attempt) &&
                Objects.equals(lastSolved, phrase.lastSolved) &&
                Objects.equals(clueLocale, phrase.clueLocale) && Objects.equals(
                solutionLocale, phrase.solutionLocale);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, clue, solution, attempt, lastSolved, timesSolved, clueLocale,
                solutionLocale);
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
}
