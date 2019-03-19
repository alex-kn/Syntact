package com.alexkn.syntact.dataaccess.phrase;

import com.alexkn.syntact.dataaccess.language.LanguagePairEntity;

import java.time.Instant;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Phrase", indices = {@Index("id"), @Index("languagePairId")},
        foreignKeys = @ForeignKey(entity = LanguagePairEntity.class, parentColumns = "id",
                childColumns = "languagePairId", onDelete = CASCADE))
public class PhraseEntity {

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
    private float easiness;

    @NonNull
    private int consecutiveCorrectAnswers;

    @NonNull
    private int timesSolved;

    @NonNull
    private Locale clueLocale;

    @NonNull
    private Locale solutionLocale;

    private Long languagePairId;

    public PhraseEntity() {

    }

    @Ignore
    public PhraseEntity(Long id, @NonNull String clue, @NonNull String solution,
            @NonNull String attempt, Instant lastSolved, @NonNull Instant nextDueDate,
            float easiness, int consecutiveCorrectAnswers, int timesSolved,
            @NonNull Locale clueLocale, @NonNull Locale solutionLocale, Long languagePairId) {

        this.id = id;
        this.clue = clue;
        this.solution = solution;
        this.attempt = attempt;
        this.lastSolved = lastSolved;
        this.nextDueDate = nextDueDate;
        this.easiness = easiness;
        this.consecutiveCorrectAnswers = consecutiveCorrectAnswers;
        this.timesSolved = timesSolved;
        this.clueLocale = clueLocale;
        this.solutionLocale = solutionLocale;
        this.languagePairId = languagePairId;
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

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

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

    @NonNull
    public Instant getNextDueDate() {

        return nextDueDate;
    }

    public void setNextDueDate(@NonNull Instant nextDueDate) {

        this.nextDueDate = nextDueDate;
    }

    public float getEasiness() {

        return easiness;
    }

    public void setEasiness(float easiness) {

        this.easiness = easiness;
    }

    public int getConsecutiveCorrectAnswers() {

        return consecutiveCorrectAnswers;
    }

    public void setConsecutiveCorrectAnswers(int consecutiveCorrectAnswers) {

        this.consecutiveCorrectAnswers = consecutiveCorrectAnswers;
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
        PhraseEntity that = (PhraseEntity) o;
        return id == that.id && Float.compare(that.easiness, easiness) == 0 &&
                consecutiveCorrectAnswers == that.consecutiveCorrectAnswers &&
                timesSolved == that.timesSolved && languagePairId == that.languagePairId &&
                Objects.equals(clue, that.clue) && Objects.equals(solution, that.solution) &&
                Objects.equals(attempt, that.attempt) &&
                Objects.equals(lastSolved, that.lastSolved) &&
                Objects.equals(nextDueDate, that.nextDueDate) &&
                Objects.equals(clueLocale, that.clueLocale) &&
                Objects.equals(solutionLocale, that.solutionLocale);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, clue, solution, attempt, lastSolved, nextDueDate, easiness,
                consecutiveCorrectAnswers, timesSolved, clueLocale, solutionLocale, languagePairId);
    }
}
