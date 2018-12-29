package com.alexkn.syntact.cardrunner.ui;

import java.util.Objects;
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

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phrase phrase = (Phrase) o;
        return id == phrase.id &&
                Objects.equals(clue, phrase.clue) &&
                Objects.equals(solution, phrase.solution);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clue, solution);
    }
}
