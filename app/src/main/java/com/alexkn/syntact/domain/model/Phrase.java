package com.alexkn.syntact.domain.model;

import com.alexkn.syntact.domain.common.Identifiable;
import com.alexkn.syntact.presentation.hangman.board.Letter;

import java.time.Instant;
import java.util.Objects;

public class Phrase implements Identifiable {

    private final int id;

    private String clue;

    private String solution;

    private String attempt;

    private Instant lastSolved;

    private int timesSolved;

    private boolean solved;

    public Phrase(int id, String clue, String solution, String attempt, Instant lastSolved,
            int timesSolved) {

        this.id = id;
        this.clue = clue;
        this.solution = solution;
        this.attempt = attempt;
        this.lastSolved = lastSolved;
        this.timesSolved = timesSolved;
        checkSolved();
    }

    public String getClue() {

        return clue;
    }

    public String getSolution() {

        return solution;
    }

    public Integer getId() {

        return id;
    }

    public void setAttempt(String attempt) {

        this.attempt = attempt;
    }

    private void checkSolved() {

        if (!attempt.contains(Letter.EMPTY.toString())) {
            solved = true;
        }
    }

    public String getAttempt() {

        return attempt;
    }

    public boolean isSolved() {

        return solved;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phrase that = (Phrase) o;
        return id == that.id && solved == that.solved && Objects.equals(clue, that.clue) &&
                Objects.equals(solution, that.solution) && Objects.equals(attempt, that.attempt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, clue, solution, attempt, solved);
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
}
