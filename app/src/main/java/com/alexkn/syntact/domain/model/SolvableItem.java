package com.alexkn.syntact.domain.model;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.alexkn.syntact.domain.common.Identifiable;

import java.time.Instant;
import java.util.Locale;
import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;

@Entity(indices = {@Index("id"), @Index("bucketId")},
        foreignKeys = @ForeignKey(entity = Bucket.class, parentColumns = "id",
                childColumns = "bucketId", onDelete = CASCADE))
public class SolvableItem implements Identifiable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

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

    @Embedded(prefix = "clue_")
    @NonNull
    private Clue clue;

    @Embedded(prefix = "solution_")
    @NonNull
    private Solution solution;

    private Long bucketId;

    @Override
    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
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
    public Clue getClue() {

        return clue;
    }

    public void setClue(@NonNull Clue clue) {

        this.clue = clue;
    }

    @NonNull
    public Solution getSolution() {

        return solution;
    }

    public void setSolution(@NonNull Solution solution) {

        this.solution = solution;
    }

    public Long getBucketId() {

        return bucketId;
    }

    public void setBucketId(Long bucketId) {

        this.bucketId = bucketId;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolvableItem that = (SolvableItem) o;
        return Objects.equals(id, that.id) && attempt.equals(that.attempt) &&
                Objects.equals(lastSolved, that.lastSolved) &&
                nextDueDate.equals(that.nextDueDate) && easiness.equals(that.easiness) &&
                consecutiveCorrectAnswers.equals(that.consecutiveCorrectAnswers) &&
                timesSolved.equals(that.timesSolved) && clue.equals(that.clue) &&
                solution.equals(that.solution) && Objects.equals(bucketId, that.bucketId);
    }

    @Override
    public int hashCode() {

        return Objects
                .hash(id, attempt, lastSolved, nextDueDate, easiness, consecutiveCorrectAnswers,
                        timesSolved, clue, solution, bucketId);
    }
}
