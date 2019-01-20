package com.alexkn.syntact.domain.model;

import com.alexkn.syntact.domain.common.Identifiable;
import com.alexkn.syntact.presentation.hangman.board.Letter;

import java.time.Instant;
import java.util.Objects;

import androidx.room.Ignore;

public class Phrase implements Identifiable {

    private int id;

    private String clue;

    private String solution;

    private String attempt;

    private Instant lastSolved;

    private int timesSolved;

    public Phrase(){

    }

    public Phrase(int id, String clue, String solution, String attempt, Instant lastSolved,
            int timesSolved) {

        this.id = id;
        this.clue = clue;
        this.solution = solution;
        this.attempt = attempt;
        this.lastSolved = lastSolved;
        this.timesSolved = timesSolved;
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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phrase phrase = (Phrase) o;
        return id == phrase.id && timesSolved == phrase.timesSolved &&
                Objects.equals(clue, phrase.clue) && Objects.equals(solution, phrase.solution) &&
                Objects.equals(attempt, phrase.attempt) &&
                Objects.equals(lastSolved, phrase.lastSolved);
    }
    @Override
    public int hashCode() {

        return Objects.hash(id, clue, solution, attempt, lastSolved, timesSolved);
    }

}
