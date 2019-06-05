package com.alexkn.syntact.domain.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.alexkn.syntact.domain.common.Identifiable;

import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = SolvableItem.class, parentColumns = "id",
        childColumns = "attemptSolvableItemId", onDelete = CASCADE))
public class Attempt implements Identifiable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "attemptId")
    private Long id;

    @NonNull
    @ColumnInfo(name = "attemptText")
    private String text;

    @NonNull
    @ColumnInfo(name = "attemptSolvableItemId", index = true)
    private Long solvableItemId;

    @Override
    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getText() {

        return text;
    }

    public void setText(String text) {

        this.text = text;
    }

    @NonNull
    public Long getSolvableItemId() {

        return solvableItemId;
    }

    public void setSolvableItemId(@NonNull Long solvableItemId) {

        this.solvableItemId = solvableItemId;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attempt attempt = (Attempt) o;
        return Objects.equals(id, attempt.id) && text.equals(attempt.text) &&
                solvableItemId.equals(attempt.solvableItemId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, text, solvableItemId);
    }
}
