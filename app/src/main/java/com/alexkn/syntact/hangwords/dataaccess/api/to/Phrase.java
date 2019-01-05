package com.alexkn.syntact.hangwords.dataaccess.api.to;

import java.time.Instant;
import java.util.Objects;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Phrase {

    @PrimaryKey
    private int id;

    private String clue;

    private String solution;

    private Instant lastSolved;

    private int timesSolved;

    public Phrase(int id) {

        this.id = id;
    }

    @Ignore
    public Phrase(String clue, String solution) {

        this.clue = clue;
        this.solution = solution;
        id = 0;
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

    public int getId() {

        return id;
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
    public int hashCode() {

        return Objects.hash(id, clue, solution, lastSolved, timesSolved);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phrase phrase = (Phrase) o;
        return id == phrase.id && timesSolved == phrase.timesSolved &&
                Objects.equals(clue, phrase.clue) && Objects.equals(solution, phrase.solution) &&
                Objects.equals(lastSolved, phrase.lastSolved);
    }
}
