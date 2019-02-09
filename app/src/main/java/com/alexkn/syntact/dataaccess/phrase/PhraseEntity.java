package com.alexkn.syntact.dataaccess.phrase;

import java.time.Instant;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Phrase")
public class PhraseEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String clue;

    @NonNull
    private String solution;

    @NonNull
    private String attempt;

    private Instant lastSolved;

    private int timesSolved;

    @NonNull
    private Locale clueLocale;

    @NonNull
    private Locale solutionLocale;

    public PhraseEntity() {

    }

    @Ignore
    public PhraseEntity(int id, @NonNull String clue, @NonNull String solution,
            @NonNull String attempt, Instant lastSolved, int timesSolved, @NonNull Locale clueLocale,
            @NonNull Locale solutionLocale) {

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

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
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

    @NonNull
    public String getAttempt() {

        return attempt;
    }

    public void setAttempt(@NonNull String attempt) {

        this.attempt = attempt;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhraseEntity that = (PhraseEntity) o;
        return id == that.id && timesSolved == that.timesSolved &&
                Objects.equals(clue, that.clue) && Objects.equals(solution, that.solution) &&
                Objects.equals(attempt, that.attempt) &&
                Objects.equals(lastSolved, that.lastSolved) && Objects.equals(
                clueLocale, that.clueLocale) &&
                Objects.equals(solutionLocale, that.solutionLocale);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, clue, solution, attempt, lastSolved, timesSolved, clueLocale,
                solutionLocale);
    }

    @NonNull
    public Locale getClueLocale() {

        return clueLocale;
    }

    public void setClueLocale(@NonNull Locale clueLocale) {

        this.clueLocale = clueLocale;
    }

    @NonNull
    public Locale getSolutionLocale() {

        return solutionLocale;
    }

    public void setSolutionLocale(@NonNull Locale solutionLocale) {

        this.solutionLocale = solutionLocale;
    }
}
