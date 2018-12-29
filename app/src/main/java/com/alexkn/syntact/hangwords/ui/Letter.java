package com.alexkn.syntact.hangwords.ui;

import com.alexkn.syntact.hangwords.util.Entity;

import java.util.concurrent.atomic.AtomicInteger;

public class Letter implements Entity {

    private static final AtomicInteger count = new AtomicInteger(0);

    private final int id;

    private Character character;

    public Letter(Character letter) {
        id = count.getAndIncrement();
        this.character = letter;
    }

    @Override
    public int getId() {
        return id;
    }

    public Character getCharacter() {
        return character;
    }
}
