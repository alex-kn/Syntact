package com.alexkn.syntact.domain.model;

import com.alexkn.syntact.domain.common.Identifiable;

import java.util.Locale;

public class ActiveLanguagePair implements Identifiable {

    private int id;

    private Locale languageLeft;

    private Locale languageRight;

    public ActiveLanguagePair() {

    }

    public ActiveLanguagePair(int id, Locale languageLeft, Locale languageRight) {

        this.id = id;
        this.languageLeft = languageLeft;
        this.languageRight = languageRight;
    }

    public void setId(int id) {

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
    public Integer getId() {

        return null;
    }
}
