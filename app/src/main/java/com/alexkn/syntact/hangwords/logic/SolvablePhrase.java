package com.alexkn.syntact.hangwords.logic;

import com.alexkn.syntact.hangwords.util.Identifiable;

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

    public int getId() {
        return id;
    }

    public void setCurrentText(String currentText) {
        this.currentText = currentText;
    }

    public String getCurrentText() {
        return currentText;
    }
}
