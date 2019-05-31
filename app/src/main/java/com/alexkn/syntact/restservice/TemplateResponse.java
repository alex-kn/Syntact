package com.alexkn.syntact.restservice;

import com.alexkn.syntact.domain.common.TemplateType;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Locale;

public class TemplateResponse {

    private Long id;

    private String name;

    private String description;

    @SerializedName("template_type")
    private TemplateType templateType;

    private Locale language;

    private List<PhraseResponse> phrases;



    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public TemplateType getTemplateType() {

        return templateType;
    }

    public void setTemplateType(TemplateType templateType) {

        this.templateType = templateType;
    }

    public Locale getLanguage() {

        return language;
    }

    public void setLanguage(Locale language) {

        this.language = language;
    }

    public List<PhraseResponse> getPhrases() {

        return phrases;
    }

    public void setPhrases(List<PhraseResponse> phrases) {

        this.phrases = phrases;
    }
}
