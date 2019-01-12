package com.alexkn.syntact.domain.model;

import com.alexkn.syntact.presentation.hangman.board.Letter;
import com.alexkn.syntact.domain.common.Identifiable;

import java.util.Objects;

public class Phrase implements Identifiable {

    private final int id;

    private String clue;
    private String solution;
    private String currentAttempt;
    private boolean solved;

    public Phrase(int id, String clue, String solution, String currentAttempt) {
        this.id = id;
        this.clue = clue;
        this.solution = solution;
        this.currentAttempt = currentAttempt;
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

    public void setCurrentAttempt(String currentAttempt) {
        this.currentAttempt = currentAttempt;
    }

    private void checkSolved() {
        if (!currentAttempt.contains(Letter.EMPTY.toString())) {
            solved = true;
        }
    }

    public String getCurrentAttempt() {
        return currentAttempt;
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
                Objects.equals(solution, that.solution) &&
                Objects.equals(currentAttempt, that.currentAttempt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, clue, solution, currentAttempt, solved);
    }

}
