package com.alexkn.syntact.domain.common;

public enum GameMode {

    DRAG("Drag"),

    TYPE("Type"),

    VOICE("Voice");

    String value;

    GameMode(String value) {

        this.value = value;
    }
}
