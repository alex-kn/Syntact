package com.alexkn.syntact.domain.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.alexkn.syntact.domain.common.Identifiable;

import java.time.Instant;
import java.util.Locale;
import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;

@Entity(indices = {@Index("id"), @Index("languagePairId")},
        foreignKeys = @ForeignKey(entity = LanguagePair.class, parentColumns = "id",
                childColumns = "languagePairId", onDelete = CASCADE))
public class Phrase implements Identifiable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @NonNull
    private String clue;

    @NonNull
    private String solution;

    @NonNull
    private String attempt;

    private Instant lastSolved;

    @NonNull
    private Instant nextDueDate;

    @NonNull
    private Float easiness;

    @NonNull
    private Integer consecutiveCorrectAnswers;

    @NonNull
    private Integer timesSolved;

    @NonNull
    private Locale clueLocale;

    @NonNull
    private Locale solutionLocale;

    private Long languagePairId;

    @Override
    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    @NonNull
    public String getClue() {

        return clue;
    }

    public void setClue(@NonNull String clue) {

        this.clue = clue;
    }

    @NonNull
    public String getSolution() {

        return solution;
    }

    public void setSolution(@NonNull String solution) {

        this.solution = solution;
    }

    @NonNull
    public String getAttempt() {

        return attempt;
    }

    public void setAttempt(@NonNull String attempt) {

        this.attempt = attempt;
    }

    public Instant getLastSolved() {

        return lastSolved;
    }

    public void setLastSolved(Instant lastSolved) {

        this.lastSolved = lastSolved;
    }

    @NonNull
    public Instant getNextDueDate() {

        return nextDueDate;
    }

    public void setNextDueDate(@NonNull Instant nextDueDate) {

        this.nextDueDate = nextDueDate;
    }

    @NonNull
    public Float getEasiness() {

        return easiness;
    }

    public void setEasiness(@NonNull Float easiness) {

        this.easiness = easiness;
    }

    @NonNull
    public Integer getConsecutiveCorrectAnswers() {

        return consecutiveCorrectAnswers;
    }

    public void setConsecutiveCorrectAnswers(@NonNull Integer consecutiveCorrectAnswers) {

        this.consecutiveCorrectAnswers = consecutiveCorrectAnswers;
    }

    @NonNull
    public Integer getTimesSolved() {

        return timesSolved;
    }

    public void setTimesSolved(@NonNull Integer timesSolved) {

        this.timesSolved = timesSolved;
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

    public Long getLanguagePairId() {

        return languagePairId;
    }

    public void setLanguagePairId(Long languagePairId) {

        this.languagePairId = languagePairId;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phrase phrase = (Phrase) o;
        return Objects.equals(id, phrase.id) && clue.equals(phrase.clue) &&
                solution.equals(phrase.solution) && attempt.equals(phrase.attempt) &&
                Objects.equals(lastSolved, phrase.lastSolved) &&
                nextDueDate.equals(phrase.nextDueDate) && easiness.equals(phrase.easiness) &&
                consecutiveCorrectAnswers.equals(phrase.consecutiveCorrectAnswers) &&
                timesSolved.equals(phrase.timesSolved) && clueLocale.equals(phrase.clueLocale) &&
                solutionLocale.equals(phrase.solutionLocale) &&
                Objects.equals(languagePairId, phrase.languagePairId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, clue, solution, attempt, lastSolved, nextDueDate, easiness,
                consecutiveCorrectAnswers, timesSolved, clueLocale, solutionLocale, languagePairId);
    }
}
