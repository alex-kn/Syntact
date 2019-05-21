package com.alexkn.syntact.restservice;

import java.util.List;
import java.util.Locale;

public class PhraseResponse {

    private Long id;

    private String text;

    private Integer difficulty;

    private List<Translation> translations;

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

    public Integer getDifficulty() {

        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {

        this.difficulty = difficulty;
    }

    public List<Translation> getTranslations() {

        return translations;
    }

    public void setTranslations(List<Translation> translations) {

        this.translations = translations;
    }

    public class Translation {

        private String text;

        private Locale language;

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
}
