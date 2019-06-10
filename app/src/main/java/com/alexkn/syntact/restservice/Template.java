package com.alexkn.syntact.restservice;

import com.alexkn.syntact.domain.common.TemplateType;
import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.Locale;

public class Template {

    private String id;

    private String name;

    @SerializedName("template_type")
    private TemplateType templateType;

    private Locale language;

    @SerializedName("phrases")
    private String phrasesUrl;

    @SerializedName("phrases_count")
    private Integer count;

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

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

    public String getPhrasesUrl() {

        return phrasesUrl;
    }

    public void setPhrasesUrl(String phrasesUrl) {

        this.phrasesUrl = phrasesUrl;
    }

    public Integer getCount() {

        return count;
    }

    public void setCount(Integer count) {

        this.count = count;
    }
}
