package com.alexkn.syntact.cardrunner.ui;

import java.util.concurrent.atomic.AtomicInteger;

class Phrase {

    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id;

    private String clue;

    private String solution;

    Phrase(String clue, String solution) {
        this.clue = clue;
        this.solution = solution;
        id = count.getAndIncrement();
    }

    public String getClue() {
        return clue;
    }

    public String getSolution() {
        return solution;
    }
}
