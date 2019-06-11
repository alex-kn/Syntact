package com.alexkn.syntact.restservice;

import com.alexkn.syntact.domain.common.TemplateType;
import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class TemplateRequest {

    private String name;

    @SerializedName("template_type")
    private TemplateType templateType;

    private Locale language;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
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
}
