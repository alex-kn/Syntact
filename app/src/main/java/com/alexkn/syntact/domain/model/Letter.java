package com.alexkn.syntact.domain.model;

import com.alexkn.syntact.domain.common.Identifiable;
import com.alexkn.syntact.domain.common.LetterColumn;

import java.util.Objects;

import androidx.annotation.NonNull;

public class Letter implements Identifiable {

    private Long id;

    private Character character;

    private LetterColumn letterColumn;

    private Long languagePairId;

    public Letter(){

    }

    public Letter(Long id, Character character, LetterColumn letterColumn, Long languagePairId) {

        this.id = id;
        this.character = character;
        this.letterColumn = letterColumn;
        this.languagePairId = languagePairId;
    }

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

    public Character getCharacter() {

        return character;
    }

    public void setCharacter(Character character) {

        this.character = character;
    }

    public LetterColumn getLetterColumn() {

        return letterColumn;
    }

    public void setLetterColumn(LetterColumn letterColumn) {

        this.letterColumn = letterColumn;
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
        Letter letter = (Letter) o;
        return Objects.equals(id, letter.id) && Objects.equals(character, letter.character) &&
                letterColumn == letter.letterColumn &&
                Objects.equals(languagePairId, letter.languagePairId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, character, letterColumn, languagePairId);
    }
}

