package com.alexkn.syntact.domain.model;

import androidx.annotation.NonNull;

import java.util.Locale;
import java.util.Objects;

public class Clue {

    @NonNull
    private String text;

    private Locale language;

    @NonNull
    public String getText() {

        return text;
    }

    public void setText(@NonNull String text) {

        this.text = text;
    }

    public Locale getLanguage() {

        return language;
    }

    public void setLanguage(Locale language) {

        this.language = language;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clue clue = (Clue) o;
        return text.equals(clue.text) && Objects.equals(language, clue.language);
    }

    @Override
    public int hashCode() {

        return Objects.hash(text, language);
    }
}
