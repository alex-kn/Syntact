package com.alexkn.syntact.restservice;

import java.util.Locale;

public class PhrasesRequest {

    private String text;

    private Locale language;

    public PhrasesRequest(String text, Locale language) {

        this.text = text;
        this.language = language;
    }

    public String getText() {

        return text;
    }

    public void setText(String text) {

        this.text = text;
    }

    public Locale getLanguage() {

        return language;
    }

    public void setLanguage(Locale language) {

        this.language = language;
    }
}
