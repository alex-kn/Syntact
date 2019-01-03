package com.alexkn.syntact.hangwords.logic;

import com.alexkn.syntact.hangwords.util.Identifiable;

import java.util.Objects;

public class SolvablePhrase implements Identifiable {

    private final int id;

    private String clue;
    private String solution;
    private String currentText;

    public SolvablePhrase(int id, String clue, String solution, String currentText) {
        this.id = id;
        this.clue = clue;
        this.solution = solution;
        this.currentText = currentText;
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
    }

    public String getCurrentText() {
        return currentText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolvablePhrase that = (SolvablePhrase) o;
        return id == that.id && Objects.equals(clue, that.clue) &&
                Objects.equals(solution, that.solution) &&
                Objects.equals(currentText, that.currentText);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, clue, solution, currentText);
    }
}
