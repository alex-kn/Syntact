package com.alexkn.syntact.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = SolvableItem.class, parentColumns = "id",
        childColumns = "clueSolvableItemId", onDelete = CASCADE))
public class Clue {

    @PrimaryKey
    @ColumnInfo(name = "clueId")
    private Long id;

    @NonNull
    @ColumnInfo(name = "clueText")
    private String text;

    @NonNull
    @ColumnInfo(name = "clueSolvableItemId", index = true)
    private Long solvableItemId;

    @NonNull
    public String getText() {

        return text;
    }

    public void setText(@NonNull String text) {

        this.text = text;
    }

    @NonNull
    public Long getSolvableItemId() {

        return solvableItemId;
    }

    public void setSolvableItemId(@NonNull Long solvableItemId) {

        this.solvableItemId = solvableItemId;
    }

    public Long getId() {


        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clue clue = (Clue) o;
        return Objects.equals(id, clue.id) && text.equals(clue.text) &&
                solvableItemId.equals(clue.solvableItemId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, text, solvableItemId);
    }
}
