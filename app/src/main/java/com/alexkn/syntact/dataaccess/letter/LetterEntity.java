package com.alexkn.syntact.dataaccess.letter;

import com.alexkn.syntact.dataaccess.language.LanguagePairEntity;
import com.alexkn.syntact.domain.common.LetterColumn;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Letter", indices = {@Index("id"), @Index("languagePairId")},
        foreignKeys = @ForeignKey(entity = LanguagePairEntity.class, parentColumns = "id",
                childColumns = "languagePairId", onDelete = CASCADE))
public class LetterEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @NonNull
    private Character character;

    @NonNull
    private LetterColumn letterColumn;

    private Long languagePairId;

    public LetterEntity() {

    }

    @Ignore
    public LetterEntity(Long id, Character character, LetterColumn letterColumn,
            Long languagePairId) {

        this.id = id;
        this.character = character;
        this.letterColumn = letterColumn;
        this.languagePairId = languagePairId;
    }

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

    public Long getLanguagePairId() {

        return languagePairId;
    }

    public void setLanguagePairId(Long languagePairId) {

        this.languagePairId = languagePairId;
    }
}
