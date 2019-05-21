package com.alexkn.syntact.domain.model;

import androidx.annotation.NonNull;

import java.util.Locale;
import java.util.Objects;

public class Solution {

    @NonNull
    private String text;

    private Locale language;

    public Solution() {

    }

    public Solution(@NonNull String text, Locale language) {

        this.text = text;
        this.language = language;
    }

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
        Solution solution = (Solution) o;
        return text.equals(solution.text) && Objects.equals(language, solution.language);
    }

    @Override
    public int hashCode() {

        return Objects.hash(text, language);
    }
}
