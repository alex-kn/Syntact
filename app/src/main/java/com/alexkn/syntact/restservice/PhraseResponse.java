package com.alexkn.syntact.restservice;

import java.util.List;
import java.util.Locale;

public class PhraseResponse {

    private Long id;

    private String text;

    private Integer template;

    private List<Translation> translations;

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

    public Integer getTemplate() {

        return template;
    }

    public void setTemplate(Integer template) {

        this.template = template;
    }

    public List<Translation> getTranslations() {

        return translations;
    }

    public void setTranslations(List<Translation> translations) {

        this.translations = translations;
    }

    public Locale getLanguage() {

        return language;
    }

    public void setLanguage(Locale language) {

        this.language = language;
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
