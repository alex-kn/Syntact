package com.alexkn.syntact.domain.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.alexkn.syntact.domain.common.Identifiable;

import java.time.Instant;
import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Bucket.class, parentColumns = "id",
        childColumns = "bucketId", onDelete = CASCADE))
public class SolvableItem implements Identifiable {

    @PrimaryKey
    private Long id;

    @NonNull
    @ColumnInfo(name = "solvableItemText")
    private String text;

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
    private Long bucketId;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    @NonNull
    public String getText() {

        return text;
    }

    public void setText(@NonNull String text) {

        this.text = text;
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
    public Long getBucketId() {

        return bucketId;
    }

    public void setBucketId(@NonNull Long bucketId) {

        this.bucketId = bucketId;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolvableItem that = (SolvableItem) o;
        return Objects.equals(id, that.id) && text.equals(that.text) &&
                Objects.equals(lastSolved, that.lastSolved) &&
                nextDueDate.equals(that.nextDueDate) && easiness.equals(that.easiness) &&
                consecutiveCorrectAnswers.equals(that.consecutiveCorrectAnswers) &&
                timesSolved.equals(that.timesSolved) && bucketId.equals(that.bucketId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, text, lastSolved, nextDueDate, easiness, consecutiveCorrectAnswers,
                timesSolved, bucketId);
    }
}
