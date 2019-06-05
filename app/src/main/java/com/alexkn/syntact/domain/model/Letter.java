package com.alexkn.syntact.domain.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.alexkn.syntact.domain.common.Identifiable;
import com.alexkn.syntact.domain.common.LetterColumn;

import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Bucket.class, parentColumns = "id",
                childColumns = "bucketId", onDelete = CASCADE))
public class Letter implements Identifiable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @NonNull
    private Character character;

    @NonNull
    private LetterColumn letterColumn;

    @ColumnInfo(index = true)
    private Long bucketId;

    @NonNull
    @Override
    public String toString() {

        return character.toString();
    }

    @Override
    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    @NonNull
    public Character getCharacter() {

        return character;
    }

    public void setCharacter(@NonNull Character character) {

        this.character = character;
    }

    @NonNull
    public LetterColumn getLetterColumn() {

        return letterColumn;
    }

    public void setLetterColumn(@NonNull LetterColumn letterColumn) {

        this.letterColumn = letterColumn;
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
        Letter letter = (Letter) o;
        return Objects.equals(id, letter.id) && character.equals(letter.character) &&
                letterColumn == letter.letterColumn &&
                Objects.equals(bucketId, letter.bucketId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, character, letterColumn, bucketId);
    }
}

