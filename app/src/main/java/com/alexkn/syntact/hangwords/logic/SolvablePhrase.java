package com.alexkn.syntact.hangwords.logic;

import com.alexkn.syntact.hangwords.ui.util.Letter;
import com.alexkn.syntact.hangwords.util.Identifiable;

import java.util.Objects;

public class SolvablePhrase implements Identifiable {

    private final int id;

    private String clue;
    private String solution;
    private String currentText;
    private boolean solved;

    public SolvablePhrase(int id, String clue, String solution, String currentText) {
        this.id = id;
        this.clue = clue;
        this.solution = solution;
        this.currentText = currentText;
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

    public void setCurrentText(String currentText) {
        this.currentText = currentText;
        checkSolved();
    }

    private void checkSolved() {
        if (!currentText.contains(Letter.EMPTY.toString())) {
            solved = true;
        }
    }

    public String getCurrentText() {
        return currentText;
    }

    public boolean isSolved() {
        return solved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolvablePhrase that = (SolvablePhrase) o;
        return id == that.id && solved == that.solved && Objects.equals(clue, that.clue) &&
                Objects.equals(solution, that.solution) &&
                Objects.equals(currentText, that.currentText);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, clue, solution, currentText, solved);
    }

}
