package com.alexkn.syntact.domain.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;

@Entity(indices = {@Index("id"), @Index("bucketId")},
        foreignKeys = @ForeignKey(entity = Bucket.class, parentColumns = "id",
                childColumns = "bucketId", onDelete = CASCADE))
public class SolvableTranslation extends SolvableItem {

    @NonNull
    private String clue;

    @NonNull
    private String solution;

    @NonNull
    private String attempt;

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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SolvableTranslation that = (SolvableTranslation) o;
        return clue.equals(that.clue) && solution.equals(that.solution) &&
                attempt.equals(that.attempt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), clue, solution, attempt);
    }
}
