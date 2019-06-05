package com.alexkn.syntact.restservice;

import java.util.List;
import java.util.Locale;

public class Phrase {

    private Long id;

    private String text;

    private Locale language;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
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
