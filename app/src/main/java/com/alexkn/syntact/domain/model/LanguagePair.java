package com.alexkn.syntact.domain.model;

import com.alexkn.syntact.domain.common.Identifiable;

import java.util.Locale;

public class LanguagePair implements Identifiable {

    private Long id;

    private Locale languageLeft;

    private Locale languageRight;

    private int score;

    public LanguagePair() {

    }

    public LanguagePair(Long id, Locale languageLeft, Locale languageRight, Integer score) {

        this.id = id;
        this.languageLeft = languageLeft;
        this.languageRight = languageRight;
        this.score = score;
    }

    public int getScore() {

        return score;
    }

    public void setScore(int score) {

        this.score = score;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Locale getLanguageLeft() {

        return languageLeft;
    }

    public void setLanguageLeft(Locale languageLeft) {

        this.languageLeft = languageLeft;
    }

    public Locale getLanguageRight() {

        return languageRight;
    }

    public void setLanguageRight(Locale languageRight) {

        this.languageRight = languageRight;
    }

    @Override
    public Long getId() {

        return id;
    }
}
