package com.alexkn.syntact.hangwords.ui.util;

import com.alexkn.syntact.hangwords.util.Identifiable;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.NonNull;

public class Letter implements Identifiable {

    public static final Character EMPTY ='_';

    private static final AtomicInteger count = new AtomicInteger(0);

    private final int id;

    private Character character;

    public Letter(Character letter) {
        this.id = count.getAndIncrement();
        this.character = letter;
    }

    @NonNull
    @Override
    public String toString() {
        return character.toString();
    }

    public Character getCharacter() {
        return character;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Letter letter = (Letter) o;
        return id == letter.id && Objects.equals(character, letter.character);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, character);
    }
}
