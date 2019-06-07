package com.alexkn.syntact.restservice;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class Phrase {

    private Long id;

    private String text;

    private Locale language;

    @SerializedName("translations")
    private String translationsUrl;

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

    public String getTranslationsUrl() {

        return translationsUrl;
    }

    public void setTranslationsUrl(String translationsUrl) {

        this.translationsUrl = translationsUrl;
    }
}
